## To activate vision components:

```java
Vision vision = new Vision
vision.startVision();
```

To start lineup

```java
Vision vision = new Vision
vision.startPIDDrive();
```

Turn off vision components

```java
Vision vision = new Vision
vision.stopVision();
```

Check if PID is completed and automatically turn off PID loop if done

```java
Vision vision = new Vision
boolean complete = vision.checkAlign();
```

Manually stop PID loop
```java
Vision vision = new Vision
vision.stopVisionPID();
```

## Exceptions


#### visionErrorException

Thrown during when a communication error occurs such as errors verifying the Pi is online.


#### visionErrorDetectionException

Thrown when the Pi reports detecting less than 2 vision targets being detected, which makes Vision unable to run.
