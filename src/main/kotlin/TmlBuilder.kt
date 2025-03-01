object TmlBuilder {
    @Composable
    fun TmlContainer(content: @Composable TmlScope.() -> Unit) {
        TmlScope("t:container").apply(content).print()
    }

    @Composable
    fun TmlScope.Tr(content: @Composable TmlScope.() -> Unit) { child("tr", content) }
    @Composable
    fun TmlScope.Th(content: @Composable TmlScope.() -> Unit) { child("th", content) }
    @Composable
    fun TmlScope.Label(forId: String) { child("t:label") { attribute("for", forId) } }
    @Composable
    fun TmlScope.Td(content: @Composable TmlScope.() -> Unit) { child("td", content) }
    @Composable
    fun TmlScope.Checkbox(id: String, value: String, className: String) {
        child("t:checkbox") {
            attribute("t:id", id)
            attribute("t:value", value)
            attribute("class", className)
        }
    }
}
