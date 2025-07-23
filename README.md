# DummyGen

A zero-dependency Java library to generate meaningful dummy data for any POJO class. Perfect for unit tests, prototyping, or UI development where realistic mock data is needed.

## Features

- 🎯 **Zero Dependencies**: No external libraries required
- 🌍 **Multi-language Support**: English and Turkish language support
- 🧠 **Context-Aware Generation**: Generates realistic data based on field names
- 🔄 **Recursive Object Population**: Handles nested POJOs automatically
- 📊 **Collection Support**: Generates Lists, Sets, Maps with dummy data
- ⚙️ **Flexible Configuration**: Customizable generation rules
- 🎲 **Smart Type Detection**: Handles primitives, dates, enums, and more
- 📦 **Easy Integration**: Works with both Maven and Gradle

## Quick Start


### Basic Usage

```java
import com.bartu.dummygen.DummyGen;

// Generate with default settings (English, meaningful content)
User user = DummyGen.generate(User.class);

// Generated user will have realistic values like:
// name: "Bartu Can"
// email: "bartu@gmail.com" 
// age: 25
// birthDate: 2000-12-12
```

### Advanced Configuration

```java
import com.bartu.dummygen.DummyGen;
import com.bartu.dummygen.config.DummyConfig;

// Turkish language with meaningful content
DummyConfig config = DummyConfig.builder()
    .language("tr")
    .meaningfulContent(true)
    .maxListSize(5)
    .allowNullFields(false)
    .build();

User turkishUser = DummyGen.generate(User.class, config);
// name: "Simge Aydın"
// email: "simge.aydin@gmail.com"

// Random gibberish mode
DummyConfig gibberishConfig = DummyConfig.builder()
    .meaningfulContent(false)
    .build();

User randomUser = DummyGen.generate(User.class, gibberishConfig);
// name: "xkjfhsdf qwertlkjh"
// email: "abcdefgh@zxcvbnm.com"
```

## Supported Types

### Primitives & Wrappers
- `String` - Context-aware generation (names, emails, addresses, etc.)
- `int/Integer`, `long/Long`, `double/Double`, `float/Float` - Realistic ranges
- `boolean/Boolean` - Random true/false

### Date & Time
- `java.util.Date`
- `java.time.LocalDate`
- `java.time.LocalDateTime`

### Collections
- `List<T>` - Generates 1-3 elements by default
- `Set<T>` - Unique elements
- `Map<K,V>` - Key-value pairs
- `Optional<T>` - Sometimes empty, sometimes filled

### Complex Types
- **Enums** - Random enum value selection
- **Nested POJOs** - Recursive generation
- **Custom Classes** - Any class with default constructor

## Context-Aware Generation

DummyGen analyzes field names to generate appropriate data:

```java
public class Person {
    private String name;        // "Jon Snow" 
    private String email;       // "jon.snow@gmail.com"
    private String username;    // "jonsnow123"
    private String city;        // "Dorne"
    private String address;     // "123 Main Street, Dorne"
    private String phoneNumber; // "+1 555 123 4567"
    private int age;           // 24 (realistic age range)
    private int salary;        // 65000 (realistic salary range)
    private Date birthDate;    // 1998-05-12 (realistic birth date)
    private Date createdAt;    // 2023-08-15 (recent creation date)
}
```

## Configuration Options

| Option | Type | Default | Description |
|--------|------|---------|-------------|
| `language` | String | "en" | Language code ("en" or "tr") |
| `meaningfulContent` | boolean | true | Use real names/words vs gibberish |
| `maxListSize` | int | 3 | Maximum size for generated collections |
| `allowNullFields` | boolean | false | Allow null values in fields |

## Example Classes

### Simple POJO
```java
public class User {
    private String name;
    private String email;
    private int age;
    private Date registrationDate;
    private boolean active;
    
    // constructors, getters, setters...
}

User user = DummyGen.generate(User.class);
```

### Complex Nested Object
```java
public class Company {
    private String name;
    private Address headquarters;
    private List<Employee> employees;
    private Map<String, Department> departments;
    
    // constructors, getters, setters...
}

Company company = DummyGen.generate(Company.class);
// Will populate all nested objects and collections
```

### With Enums and Optional
```java
public class Product {
    private String name;
    private ProductStatus status;
    private Optional<String> description;
    private List<String> tags;
    
    // constructors, getters, setters...
}
```

## Requirements

- Java 8 or higher
- No external dependencies

## Building from Source

```bash
git clone https://github.com/bartucank/dummygen.git
cd dummygen
./gradlew build
```

## Running Tests

```bash
./gradlew test
```

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Changelog

### v1.0.0
- Initial release
- Support for basic types, collections, and nested objects  
- English and Turkish language support
- Context-aware field generation
- Zero external dependencies
