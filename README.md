# Java AWS Lambda GraalVM

Sample app created to get GraalVM build using (1) Java 21, (2) AWS Lambda (3) Gradle build.

Related stackoverflow question - 

* https://stackoverflow.com/questions/79558237/gradle-plugin-which-generate-a-graalvm-binary-of-a-java-21-aws-lambda

## Building Normal Java JAR (Zip) Lambda

To build normal JAva JAR Lambda run:

```shell
./gradlew clean buildZip
```

This creates a Zip file containing the Lambda - `build/distributions/java-aws-lambda-graalvm.zip`.

This can be uploaded via:

* _AWS -> Functions -> Create function_

With details 

* **Function Name** - `hello-java21`
* **Runtime** - `Java21`
* **Handler** - `lambda.test.HelloLambdaHandler`
* **Architecture** - `x86_64`

Now upload the `build/distributions/java-aws-lambda-graalvm.zip` and set your Event JSON as:

```json
{
  "pathParameters": {
    "name": "Jimmy"
  }
}
```

Now click the **Test** button, you should see:

```json
{
  "statusCode": 200,
  "headers": {
    "Content-Type": "application/json"
  },
  "body": "{\"message\": \"Hello World\"}",
  "isBase64Encoded": false
}
```

with stats like

* **Init duration** - `602.94 ms`
* **Duration** - `51.52 ms`

Click test a few more times and duration should go to something small like `1.50 ms`.


## Building GraalVM image

To build a GraalVM image using Micronaut add the following to the *build.gradle* file:

```groovy
application {
    mainClass.set('com.amazonaws.services.lambda.runtime.api.client.AWSLambda')
}

graalvmNative {
    binaries {
        main {
            imageName = "native"
            verbose = true
            fallback = false
        }
    }
}

// Build ZIP file which includes the `bootstrap` script (required for AWS Lambdas) and `native` GraalVM executable
task buildNativeLambda(type: Zip) {
    archiveBaseName = rootProject.name + "-native"
    from(files('build/native/nativeCompile/native'))
    from(files('build/resources/main/bootstrap'))
}

buildNativeLambda.dependsOn nativeCompile
```

## GraalVM Reflect Files (using SAM CLI)

Note, the reflection files were created using the [SAM CLI](https://github.com/aws/aws-sam-cli) tool:

```shell
sam init
```

Then selecting the follow when it runs:

```

Which template source would you like to use?
        1 - AWS Quick Start Templates
        2 - Custom Template Location
        
Choice: 1

Choose an AWS Quick Start application template
        1 - Hello World Example
        2 - Data processing
        ...
        
Template: 1

Use the most popular runtime and package type? (python3.13 and zip) [y/N]: n

Which runtime would you like to use?
        1 - dotnet8
        2 - dotnet6
        3 - go (provided.al2)
        4 - go (provided.al2023)
        5 - graalvm.java11 (provided.al2)
        6 - graalvm.java17 (provided.al2)
        7 - java21
        8 - java17
        9 - java11
        ...
        
Runtime: 6

Based on your selections, the only Package type available is Zip.
We will proceed to selecting the Package type as Zip.

Which dependency manager would you like to use?
        1 - gradle
        2 - maven
        
Dependency manager: 1

...
```

Select `n` for the remaining options and copy the entire `META-INF` from the `src/main/resources` from the generated
application into the `src/main/resources` here.

Finally, edit the `META-INF/native-image/helloworld/reflect-config.json` file to align with `HelloLambdaHandler`.