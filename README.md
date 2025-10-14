# markdown2tg
A lightweight Java library that transforms standard [Markdown](https://markdownguide.offshoot.io/basic-syntax/) into [Telegram's MarkdownV2](https://core.telegram.org/bots/api#markdownv2-style) format for safe and styled message rendering

## Features
    - Headings → bold text
    - Numbered lists are preserved (`1. item`)
    - Unordered lists: any of `* + -` → `-`
    - Escaping for Telegram MarkdownV2; URL-encode spaces and parentheses in links
     
## Installation

In `build.gradle`:

```groovy
dependencies {
    api 'org.apache.commons:commons-text:1.14.0'
}
```

## Usage

```java
import cane.brothers.markdown.convert.TelegramMarkdownConverter;

TelegramMarkdownConverter c = new TelegramMarkdownConverter();
String out = c.convert("# Title\n\n*Hello* **world**. [link](https://example.com)");
```

## Links
- Markdown Basic Syntax: https://markdownguide.offshoot.io/basic-syntax/
- Telegram MarkdownV2: https://core.telegram.org/bots/api#markdownv2-style
- telegramify-markdown Python library: https://github.com/sudoskys/telegramify-markdown/tree/main?tab=readme-ov-file
