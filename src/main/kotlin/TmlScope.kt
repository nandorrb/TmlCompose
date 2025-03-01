import androidx.compose.runtime.Composable
import org.apache.tapestry5.corelib.components.ActionLink
import org.apache.tapestry5.corelib.components.Form
import java.lang.reflect.Field

class TmlScope(private val tag: String) {
    private val attributes = mutableMapOf<String, String>()
    private val children = mutableListOf<TmlScope>()

    // Provide `t` to enforce Tapestry components access
    val t = TmlTapestryScope(this)

    fun attribute(name: String, value: String) {
        attributes[name] = value
    }

    @Composable
    fun child(tag: String, content: @Composable TmlScope.() -> Unit): TmlScope {
        val childScope = TmlScope(tag).apply(content)
        children.add(childScope)
        return childScope
    }

    fun print(): String {
        val attrString = if (attributes.isNotEmpty()) attributes.entries.joinToString(" ") { """${it.key}="${it.value}"""" } else ""
        val childString = children.joinToString("\n") { it.print() }
        return if (children.isEmpty()) {
            "<$tag${if (attrString.isNotEmpty()) " $attrString" else ""} />"
        } else {
            "<$tag${if (attrString.isNotEmpty()) " $attrString" else ""}>\n$childString\n</$tag>"
        }
    }
}

/**
 * `t.` namespace for Tapestry components, restricting their usage to `t.form`, `t.actionlink`, etc.
 */
class TmlTapestryScope(private val scope: TmlScope) {
    @Composable
    fun form(
        zone: String? = null,
        secure: Boolean? = null,
        validationId: String? = null,
        content: @Composable TmlScope.() -> Unit = {}
    ) {
        scope.child("t:form") {
            addAttributesFromClass(Form::class.java, mapOf(
                "zone" to zone,
                "secure" to secure?.toString(),
                "validationId" to validationId
            ))

            content()
        }
    }

    @Composable
    fun actionlink(
        event: String? = null,
        context: String? = null,
        content: @Composable TmlScope.() -> Unit = {}
    ) {
        scope.child("t:actionlink") {
            addAttributesFromClass(
                ActionLink::class.java, mapOf(
                    "event" to event,
                    "context" to context
                )
            )

            content()
        }
    }

    private fun TmlScope.addAttributesFromClass(clazz: Class<*>, attributes: Map<String, Any?>) {
        for (field: Field in clazz.declaredFields) {
            field.isAccessible = true  // Allow access to private fields

            // Check if the field has @Parameter annotation
            val hasParameterAnnotation = field.annotations.any { it.annotationClass.simpleName == "Parameter" }

            if (hasParameterAnnotation) {
                val name = field.name
                val value = attributes[name]?.toString()
                if (!value.isNullOrEmpty()) {
                    attribute("t:$name", value)
                }
            }
        }
    }
}
