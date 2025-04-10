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

```

