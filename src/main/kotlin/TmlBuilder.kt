import androidx.compose.runtime.Composable
import java.lang.reflect.Field

@Target(AnnotationTarget.PROPERTY)
annotation class Parameter(val required: Boolean = false)

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
     * Accepts a Java class and dynamically extracts parameters.
     */
    @Composable
    fun TmlScope.TapestryComponent(
        clazz: Class<*>,
        attributes: Map<String, Any?> = emptyMap(),
        content: @Composable TmlScope.() -> Unit = {}
    ) {
        val tagName = clazz.simpleName.lowercase()
        child("t:$tagName") {
            addAttributesFromJavaClass(clazz, attributes)
            content()
        }
    }

    /**
     * Extracts `@Parameter` annotations from a **Java class** and applies provided attributes.
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
