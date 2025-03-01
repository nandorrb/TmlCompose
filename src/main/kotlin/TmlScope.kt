import androidx.compose.runtime.Composable

class TmlScope(private val tag: String) {
    private val attributes = mutableMapOf<String, String>()
    private val children = mutableListOf<TmlScope>()

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