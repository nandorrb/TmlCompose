import androidx.compose.runtime.Composable
import java.lang.reflect.Field

@Target(AnnotationTarget.PROPERTY)
annotation class Parameter(val required: Boolean = false)

/**
 * A dynamic builder for storing component parameters.
 */
class TapestryComponentParams {
    var zone: String? = null
    var secure: Boolean? = null
    var validationId: String? = null

    fun toMap(): Map<String, Any?> {
        return mapOf(
            "zone" to zone,
            "secure" to secure,
            "validationId" to validationId
        ).filterValues { it != null } // Remove null values
    }
}

object TmlBuilder {
    @Composable
    fun TmlContainer(content: @Composable TmlScope.() -> Unit): TmlScope {
        val scope = TmlScope("t:container")
        scope.content()
        return scope
    }

    @Composable
    fun TmlScope.tr(content: @Composable TmlScope.() -> Unit) {
        child("tr", content)
    }

    @Composable
    fun TmlScope.th(content: @Composable TmlScope.() -> Unit) {
        child("th", content)
    }

    @Composable
    fun TmlScope.td(content: @Composable TmlScope.() -> Unit) {
        child("td", content)
    }

    /**
     * **Fix: Use `FormComponent()` instead of `Form()` to avoid Java class conflict**
     */
    @Composable
    fun TmlScope.form(
        zone: String? = null,
        secure: Boolean? = null,
        validationId: String? = null,
        content: @Composable TmlScope.() -> Unit = {}
    ) {
        child("t:form") {
            if (zone != null) attribute("t:zone", zone)
            if (secure != null) attribute("t:secure", secure.toString())
            if (validationId != null) attribute("t:validationId", validationId)
            content()
        }
    }

    /**
     * Generic function for any Tapestry component.
     */
    @Composable
    fun TmlScope.TapestryComponent(
        clazz: Class<*>,
        content: TapestryComponentParams.() -> Unit = {}
    ) {
        val tagName = clazz.simpleName.lowercase()
        val params = TapestryComponentParams().apply(content)

        child("t:$tagName") {
            addAttributesFromJavaClass(clazz, params.toMap())
        }
    }

    /**
     * Extracts `@Parameter` annotations and applies only non-null, non-empty attributes.
     */
    fun TmlScope.addAttributesFromJavaClass(clazz: Class<*>, attributes: Map<String, Any?>) {
        for (field: Field in clazz.declaredFields) {
            field.isAccessible = true  // Allow access to private fields

            // Check if the field has any annotation named "Parameter"
            val hasParameterAnnotation = field.annotations.any { it.annotationClass.simpleName == "Parameter" }

            if (hasParameterAnnotation) {
                val name = field.name
                val value = attributes[name]?.toString() ?: ""
                attribute("t:$name", value)
            }
        }
    }
}
