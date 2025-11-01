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

In `build.gradle`:

```groovy
dependencies {
    api 'org.apache.commons:commons-text:1.14.0'
    api 'org.commonmark:commonmark:0.27.0'
}
```

## Usage

```java
import cane.brothers.markdown.convert.TelegramMarkdownConverter;

TelegramMarkdownConverter c = new TelegramMarkdownConverter();
String out = c.convert("# Title\n\n*Hello* **world**. [link](https://example.com)");
```

## Links
- CommonMark Specification: https://commonmark.org/
- CommonMark Java library: https://github.com/commonmark/commonmark-java
- Telegram MarkdownV2: https://core.telegram.org/bots/api#markdownv2-style
