import androidx.compose.runtime.Composable
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.findAnnotation


@Target(AnnotationTarget.PROPERTY)
annotation class Parameter(val required: Boolean = false)

@Target(AnnotationTarget.CLASS)
annotation class SupportsInformalParameters

@Target(AnnotationTarget.CLASS)
annotation class Events(val value: Array<String>)

object TmlBuilder {
    @Composable
    fun TmlContainer(content: @Composable TmlScope.() -> Unit): TmlScope {
        val scope = TmlScope("t:container")
        scope.content()
        return scope
    }

    @Composable
    fun TmlScope.Tr(content: @Composable TmlScope.() -> Unit) {
        child("tr", content)
    }

    @Composable
    fun TmlScope.Th(content: @Composable TmlScope.() -> Unit) {
        child("th", content)
    }



    @Composable
    fun TmlScope.Td(content: @Composable TmlScope.() -> Unit) {
        child("td", content)
    }



@Composable
fun TmlScope.TapestryComponent(instance: Any, content: @Composable TmlScope.() -> Unit = {}): TmlScope {
    val tagName = instance::class.simpleName?.lowercase() ?: "unknown"
    return child("t:$tagName") {
        addAttributesFromClass(instance::class.java)
        content()
    }
}

    fun TmlScope.addAttributesFromClass(clazz: Class<*>) {
        clazz.kotlin.declaredMemberProperties.forEach { property ->
            property.findAnnotation<Parameter>()?.let {
                val name = property.name
                val value = property.call(this@addAttributesFromClass)?.toString() ?: ""
                attribute("t:$name", value)
            }
        }
    }
}
