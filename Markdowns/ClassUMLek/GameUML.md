![Class UML for the Unity Game](/Markdowns/Images/GameClassUML.png)

---
#### PlantUML Script

```
@startuml


class MonoBehaviour{
    + void Start()
    + void Update()
}
class GameManager{
   - TextMeshProUGUI scoreText
   - TextMeshProUGUI timerText
   - GameObject pauseMenu
   - Animator pauseMenuAnimator
   - float actualTime
   + float score
   + bool gameOver
   + bool isPaused
   
   + void UpdateScore()
   + void UpdateTimer()
   + void Restart()
   + void Pause()
   + void Resume()
   + void PauseManager()
   + void QuitToMainMenu()
}

class PlayerController{
    - float motorForce
    - float brakeForce
    - float handBrakeForce
    + WheelCollider frontLeftWheelCollider
    + WheelCollider frontRightWheelCollider
    + WheelCollider backLeftWheelCollider
    + WheelCollider backRightWheelCollider
    + Transform frontLeftWheelTransform
    + Transform frontRightWheelTransform
    + Transform backLeftWheelTransform
    + Transform backRightWheelTransform
    - float horizontalInput
    - float verticalInput
    - float currentSteerAngle
    - bool handBrake
    - RigidBody rb
    - AnimationCurve steeringCurve
    - float speed
    - float slipAngle
    - float backSlipAngle
    - float brakeInput
    - bool isBraking
    
    - void GetInput()
    - void HandleMotor()
    - void ApplyHandBreak()
    - void ApplyBreaking()
    - void HandleSteering()
    - void UpdateSingleWheel(WheelCollider wheelCollider, Transform wheelTransform)
    - void UpdateWheels()
    - void OnCollisionEnter(Collision collision)
}


class CameraManager{
    - GameObject player
    - Vector3 offset
    - Quaternion playerRotation
    
    - void UpdateCamera()
}

class MenuCameraManager{
    - Vector2 min
    - Vector2 max
    - Vector2 yRotationRange
    - float lerpSpeed
    - float rotationSpeed
    - Vector3 newPosition
    - Vector3 newRotation
    
    - void Awake()
    - void FixedUpdate()
    - void GetNewPosition()
    - void UpdateMainMenuCamera()
}

class MenuManager{
    + void StartGame()
    + void Quit()
}

MonoBehaviour <|-- GameManager
MonoBehaviour <|-- MenuManager
MonoBehaviour <|-- CameraManager
MonoBehaviour <|-- MenuCameraManager
MonoBehaviour <|-- PlayerController

@enduml
```

