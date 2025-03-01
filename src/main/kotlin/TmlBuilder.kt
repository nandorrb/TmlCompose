import androidx.compose.runtime.Composable
import java.lang.reflect.Field

@Target(AnnotationTarget.PROPERTY)
annotation class Parameter(val required: Boolean = false)

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


    @Composable
    fun TmlScope.div(content: @Composable TmlScope.() -> Unit) {
        child("div", content)
    }

    @Composable
    fun TmlScope.p(content: @Composable TmlScope.() -> Unit) {
        child("p", content)
    }

    @Composable
    fun TmlScope.span(content: @Composable TmlScope.() -> Unit) {
        child("span", content)
    }

    @Composable
    fun TmlScope.h1(content: @Composable TmlScope.() -> Unit) {
        child("h1", content)
    }

    @Composable
    fun TmlScope.h2(content: @Composable TmlScope.() -> Unit) {
        child("h2", content)
    }

    @Composable
    fun TmlScope.h3(content: @Composable TmlScope.() -> Unit) {
        child("h3", content)
    }

    @Composable
    fun TmlScope.h4(content: @Composable TmlScope.() -> Unit) {
        child("h4", content)
    }

    @Composable
    fun TmlScope.h5(content: @Composable TmlScope.() -> Unit) {
        child("h5", content)
    }

    @Composable
    fun TmlScope.h6(content: @Composable TmlScope.() -> Unit) {
        child("h6", content)
    }

    @Composable
    fun TmlScope.ul(content: @Composable TmlScope.() -> Unit) {
        child("ul", content)
    }

    @Composable
    fun TmlScope.ol(content: @Composable TmlScope.() -> Unit) {
        child("ol", content)
    }

    @Composable
    fun TmlScope.li(content: @Composable TmlScope.() -> Unit) {
        child("li", content)
    }

    @Composable
    fun TmlScope.a(content: @Composable TmlScope.() -> Unit) {
        child("a", content)
    }

    @Composable
    fun TmlScope.img(content: @Composable TmlScope.() -> Unit) {
        child("img", content)
    }

    @Composable
    fun TmlScope.button(content: @Composable TmlScope.() -> Unit) {
        child("button", content)
    }

    @Composable
    fun TmlScope.input(content: @Composable TmlScope.() -> Unit) {
        child("input", content)
    }

    @Composable
    fun TmlScope.label(content: @Composable TmlScope.() -> Unit) {
        child("label", content)
    }

    @Composable
    fun TmlScope.form(content: @Composable TmlScope.() -> Unit) {
        child("form", content)
    }

    @Composable
    fun TmlScope.table(content: @Composable TmlScope.() -> Unit) {
        child("table", content)
    }

    @Composable
    fun TmlScope.thead(content: @Composable TmlScope.() -> Unit) {
        child("thead", content)
    }

    @Composable
    fun TmlScope.tbody(content: @Composable TmlScope.() -> Unit) {
        child("tbody", content)
    }

    @Composable
    fun TmlScope.tfoot(content: @Composable TmlScope.() -> Unit) {
        child("tfoot", content)
    }

    @Composable
    fun TmlScope.section(content: @Composable TmlScope.() -> Unit) {
        child("section", content)
    }

    @Composable
    fun TmlScope.article(content: @Composable TmlScope.() -> Unit) {
        child("article", content)
    }

    @Composable
    fun TmlScope.nav(content: @Composable TmlScope.() -> Unit) {
        child("nav", content)
    }

    @Composable
    fun TmlScope.header(content: @Composable TmlScope.() -> Unit) {
        child("header", content)
    }

    @Composable
    fun TmlScope.footer(content: @Composable TmlScope.() -> Unit) {
        child("footer", content)
    }

    @Composable
    fun TmlScope.main(content: @Composable TmlScope.() -> Unit) {
        child("main", content)
    }

    @Composable
    fun TmlScope.aside(content: @Composable TmlScope.() -> Unit) {
        child("aside", content)
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
