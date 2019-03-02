To activate vision components:

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
boolean complete = vision.drive();
```

Manually stop PID loop
```java
Vision vision = new Vision
vision.stopVisionPID();
```