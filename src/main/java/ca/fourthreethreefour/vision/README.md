## README

Start vision components

```java
Vision vision = new Vision
vision.startVision();
```

To start lineup

```java
vision.startAlignPID();
```

Turn off vision components

```java
vision.stopVision();
```

Manually stop PID loop
```java
vision.stopAlignPID();
```

Check if PID is completed and automatically turn off PID loop if done

```java
boolean complete = vision.checkAlign();
```

## Exceptions


#### visionErrorException

Thrown during when a communication error occurs such as errors verifying the Pi is online.


#### visionErrorDetectionException

Thrown when the Pi reports detecting less than 2 vision targets being detected, which makes Vision unable to run.
