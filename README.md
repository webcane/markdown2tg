# markdown2tg
A lightweight Java library that transforms common [Markdown](https://commonmark.org/help/) into [Telegram's MarkdownV2](https://core.telegram.org/bots/api#markdownv2-style) format for safe and styled message rendering

## Features
- Headings are converted to bold text
- Numbered lists are preserved (`• item`)
- Unordered lists (`*`, `+`, `-`) are converted to Telegram bullets (`- item`)
- Emphasis (`*text*` or `_text_`) is converted to italic
- Strong emphasis (`**text**` or `__text__`) is converted to bold
- Inline and block code is supported, including language annotation for code blocks
- Block quotes are supported
- Links and images are converted to Telegram MarkdownV2 format
- All special characters are escaped for Telegram MarkdownV2
- Spaces and parentheses in links are URL-encoded
- Thematic breaks (---, ***, ___) are supported
- Safe conversion for Telegram message rendering

## Installation

> **Note:** You need a GitHub personal access token (PAT) with at least `read:packages` permission to download packages from GitHub. Set it as `gpr.key` and your GitHub username as `gpr.user` in your `gradle.properties`.

In `build.gradle`:
```groovy
repositories {
    maven {
        name = "markdown2tg"
        url = uri("https://maven.pkg.github.com/webcane/markdown2tg")
        credentials {
            username = project.findProperty("gpr.user")
            password = project.findProperty("gpr.key")
        }
    }
}
```
```groovy
dependencies {
    implementation "cane.brothers:markdown2tg:${version}"
}
```

## Usage

```java
import cane.brothers.tg.md.convert.MarkdownToTelegramConverter;

MarkdownToTelegramConverter c = new MarkdownToTelegramConverter();
String out = c.convert("# Title\n\n*Hello* **world**. [link](https://example.com)");
```

## Links
- CommonMark Specification: https://commonmark.org/
- CommonMark Java library: https://github.com/commonmark/commonmark-java
- Telegram MarkdownV2: https://core.telegram.org/bots/api#markdownv2-style
