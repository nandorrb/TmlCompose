import androidx.compose.runtime.Composable

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
 * `t.` namespace for Tapestry components, restricting their usage to `t.form`, `t.actionLink`, etc.
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
            if (zone != null) attribute("t:zone", zone)
            if (secure != null) attribute("t:secure", secure.toString())
            if (validationId != null) attribute("t:validationId", validationId)
            content()
        }
    }
}
