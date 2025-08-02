![Test Coverage](.github/badges/jacoco.svg)

# FQL - Financial Query Language

**FQL (Financial Query Language)** is a domain-specific language that brings Excel-like financial formula capabilities
to Java applications.

## FQL Core Syntax

### Temporal Retrieval Patterns

| Pattern             | Syntax                                                                  |
|---------------------|-------------------------------------------------------------------------|
| **Latest value**    | `DATA(<str:entity>, <str:metric>, LATEST())`                            |
| **Absolute period** | `PERIOD(<int:year>, <str:unit>)`                                        |
| **Fixed range**     | `RANGE(PERIOD(<int:start>, <str:unit>), PERIOD(<int:end>, <str:unit>))` |
| **Relative range**  | `LAST(<int:count>, <str:unit>)`                                         |

### Example Queries

```FQL
DATA("TSLA", "SHARE_PRICE", LATEST())
DATA("AAPL", "REVENUE", PERIOD(2022, "YEAR"))
DATA("MSFT", "EPS", PERIOD(2023, "Q3"))
DATA("V", "DIVIDEND", RANGE(PERIOD(2001, "YEAR"), PERIOD(2009, "YEAR")))
DATA("MSFT", "EPS", RANGE(PERIOD(2020, "Q1"), PERIOD(2023, "Q4")))
DATA("AAPL", "REVENUE", LAST(10, "QUARTER"))
```

### Type Reference

| Placeholder    | Type    | Description             | Example     |
|----------------|---------|-------------------------|-------------|
| `<str:entity>` | String  | Entity identifier*      | `"AAPL"`    |
| `<str:metric>` | String  | Financial metric        | `"REVENUE"` |
| `<int:year>`   | Integer | Calendar year           | `2023`      |
| `<int:start>`  | Integer | Range start year        | `2001`      |
| `<int:end>`    | Integer | Range end year          | `2009`      |
| `<int:count>`  | Integer | Number of periods       | `10`        |
| `<str:unit>`   | String  | Time unit specification | `"QUARTER"` |

> **\* Entity identifiers** are strings defined by the user of the library. While ticker symbols like "AAPL" are common,
> other formats like internal IDs, names, or codes can also be used. They can include:
> 
> - Uppercase and lowercase letters (`A-Z`, `a-z`)
> - Digits (`0-9`)
> - Underscores (`_`)
> - Periods (`.`)
> - Hyphens (`-`)
> - Forward slashes (`/`)
> - Colons (`:`)
> - Spaces (` `)
> - Asterisks (`*`)
> - Carets (`^`)
> - Dollar signs (`$`)
> - Hash signs (`#`)
> - At signs (`@`)
> - Parentheses (`(`, `)`)
> - Square brackets (`[`, `]`)
> - Angle brackets (`<`, `>`)

### Function Reference

| Function     | Parameters               | Output Type  |
|--------------|--------------------------|--------------|
| **`DATA`**   | `(str, str, time_spec)`  | Value/Series |
| **`PERIOD`** | `(int, str)`             | TimePoint    |
| **`RANGE`**  | `(TimePoint, TimePoint)` | TimeInterval |
| **`LAST`**   | `(int, str)`             | TimeInterval |
| **`LATEST`** | `()`                     | TimePoint    |

**Valid Units**
`"YEAR"` | `"QUARTER"` | `"Q1"` | `"Q2"` | `"Q3"` | `"Q4"`

---

## Installation 📦

Add FQL to your project with Gradle:

```groovy
dependencies {
    implementation 'dev.sorn.fql:fql:1.0.0'
}
```

Or Maven:

```xml

<dependency>
    <groupId>dev.sorn.fql</groupId>
    <artifactId>fql</artifactId>
    <version>1.0.0</version>
</dependency>
```