import androidx.compose.runtime.Composable
import org.apache.tapestry5.corelib.components.ActionLink
import org.apache.tapestry5.corelib.components.Form
import java.lang.reflect.Field

class TmlScope(private val tag: String) {
    private val attributes = mutableMapOf<String, String>()
    private val children = mutableListOf<TmlScope>()
    private var textContent: String? = null // Stores text content for elements like <p>, <span>

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

    // Allow setting text inside elements
    operator fun String.unaryPlus() {
        textContent = this
    }

    fun print(): String {
        val attrString = if (attributes.isNotEmpty()) attributes.entries.joinToString(" ") { """${it.key}="${it.value}"""" } else ""
        val childString = children.joinToString("\n") { it.print() }
        val text = textContent?.let { " $it " } ?: ""

        return if (children.isEmpty() && textContent.isNullOrEmpty()) {
            "<$tag${if (attrString.isNotEmpty()) " $attrString" else ""} />"
        } else {
            "<$tag${if (attrString.isNotEmpty()) " $attrString" else ""}>$text$childString</$tag>"
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

    //These components are loaded in the Tapestry5 core library tapestry_5_4.xsd
    @Composable
    fun body(content: @Composable TmlScope.() -> Unit = {}) {
        scope.child("t:body", content)
    }

    @Composable
    fun extend(content: @Composable TmlScope.() -> Unit = {}) {
        scope.child("t:extend", content)
    }

    @Composable
    fun extensionPoint(id: String, content: @Composable TmlScope.() -> Unit = {}) {
        scope.child("t:extension-point") {
            attribute("id", id)
            content()
        }
    }

    @Composable
    fun replace(id: String, content: @Composable TmlScope.() -> Unit = {}) {
        scope.child("t:replace") {
            attribute("id", id)
            content()
        }
    }

    @Composable
    fun content(content: @Composable TmlScope.() -> Unit = {}) {
        scope.child("t:content", content)
    }

    @Composable
    fun remove(content: @Composable TmlScope.() -> Unit = {}) {
        scope.child("t:remove", content)
    }

    @Composable
    fun container(content: @Composable TmlScope.() -> Unit = {}) {
        scope.child("t:container", content)
    }

    @Composable
    fun block(id: String? = null, content: @Composable TmlScope.() -> Unit = {}) {
        scope.child("t:block") {
            id?.let { attribute("id", it) }
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

