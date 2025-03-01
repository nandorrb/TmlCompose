# TmlCompose

TmlCompose is a **Jetpack Compose-inspired DSL** for generating **Tapestry TML files** dynamically using **Kotlin**. It allows developers to write **declarative UI structures** in Kotlin, which are then converted into valid **Apache Tapestry** markup (TML). This project is designed to simplify **Tapestry development**, reduce **boilerplate**, and encourage **modular UI composition**.

## Why TmlCompose?

Apache Tapestry is a powerful but complex framework for Java-based web applications. Traditional **TML templates** can be hard to maintain due to their **XML-based syntax** and **tight coupling**. **TmlCompose solves these problems by:**

- Providing a **modern, declarative approach** to defining Tapestry UIs.
- Encouraging **composability** and **reusability** of UI components.
- Reducing **boilerplate** and improving **developer experience**.
- Supporting **dynamic UI generation** and **programmatic TML creation**.

## Features

✅ **Composable DSL**: Define TML components using a Jetpack Compose-like API. 

✅ **Pure Kotlin-based UI definition**: No more XML editing. 

✅ **Encapsulation & Reusability**: Create reusable UI components with ease. 

✅ **Seamless Tapestry Integration**: Outputs valid **.tml** templates for Apache Tapestry. 

✅ **Multi-platform Friendly**: Can be adapted for backend **(JVM)** and frontend **(Kotlin/JS)**.

## Example Usage

Instead of writing TML manually:

```xml
<t:container>
    <tr>
        <th><t:label for="displayTooltip"/></th>
        <td>
            <t:checkbox t:id="displayTooltip" t:value="viewReport.displayTooltip" class="modOnCheck"/>
        </td>
    </tr>
</t:container>
```

You can **write this in Kotlin** using **TmlCompose**:

```kotlin
    val newScope2 = TmlScope("t:container").apply {
        t.actionlink(event = "onClick", context = "user123") {
            t.form(
                zone = "myZone",
                secure = false,
                validationId = "myValidationId"
            ) {
                // Additional content inside Form
                div {
                    p { +"Inside form paragraph" }
                    span { +"Inside form span" } 
                }
            }

            tr {
                th {}
            }
            tr {
                td {}
            }

            div {
                p { +"Outside paragraph" }
                span { +"Outside span" }
            }
        }
    }
```

This will automatically generate the correct **TML markup**, making it easier to manage complex UIs.

## Installation & Setup

TmlCompose is still in early development. To use it in your project:

1. Clone the repository:
   ```sh
   git clone https://github.com/nandorrb/TmlCompose.git
   ```
2. Open the project in **IntelliJ IDEA** or any **Kotlin-supported IDE**.
3. Use the **Composable functions** to define Tapestry UI structures.

## Roadmap

🚀 **Phase 1**: Core DSL development for generating TML ✅ 
🚀 **Phase 2**: Expand support for all Tapestry components ✅ 
🚀 **Phase 3**: Integrate with **Tapestry Live Reload** (auto-update UI) 🔄 
🚀 **Phase 4**: Provide **NPM support** for frontend integration ⚡ 
🚀 **Phase 5**: Publish as an **open-source Kotlin library** 📦

## Contributing

We welcome contributions from the community! Feel free to:

- Submit **issues** and **feature requests**
- Open **pull requests** with improvements
- Discuss ideas in the **GitHub Discussions** tab

## License

TmlCompose is released under the **MIT License**.

## Author

👨‍💻 Created by **nandorrb** ([GitHub](https://github.com/nandorrb))

If you like this project, consider giving it a ⭐ to help it grow!

---

Let's revolutionize **Apache Tapestry development** with Kotlin! 🚀

