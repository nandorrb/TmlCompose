import TmlBuilder.TmlContainer
import TmlBuilder.div
import TmlBuilder.form
import TmlBuilder.p
import TmlBuilder.span
import TmlBuilder.td
import TmlBuilder.th
import TmlBuilder.tr



fun main() {
    val newScope2 = TmlContainer {
        t.form(
            zone = "myZone",
            secure = false,
            validationId = "myValidationId"
        ) {
            // Additional content inside Form
        }

        form {  }

        tr {
            td {}
            th {}
        }
        div{
            p{}
            span{}

        }
    }

    println(newScope2.print())
}


