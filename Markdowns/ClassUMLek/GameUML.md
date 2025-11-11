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
   - TextMeshProUGUI highScoreText
   - TextMeshProUGUI timerText
   - GameObject pauseMenu
   - Animator pauseMenuAnimator
   - GameObject gameOverMenu
   - Animator gameOverMenuAnimator
   - GameObject[] stars
   - float actualTime
   + float score
   + bool gameOver
   + bool isPaused
   
   
   + void UpdateScore()
   + void UpdateTimer()
   + void UpdateStars()
   + void SaveHighScore(float score)
   + float LoadHighScore()
   + void Restart()
   + void Pause()
   + void Resume()
   + void PauseManager()
   + void GameOver()
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
    - void LateUpdate()
    - void UpdateCamera()
}

class MenuCameraManager{
    - Vector2 min
    - Vector2 max
    - Vector2 yRotationRange
    - float lerpSpeed
    - float rotationSpeed
    - Vector3 newPosition
    - Quaternion newRotation
    
    - void Awake()
    - void FixedUpdate()
    - void GetNewPosition()
    - void UpdateMainMenuCamera()
}

class MenuManager{
    + void StartGame()
    + void Quit()
}

class EnemySpawnManager{
    - GameObject police
    - int starCount
    - int policeCount
    + Transform[] spawnPositions
    
    + void SpawnPolice()
}

class AgentScript{
    - NavMeshAgent agent
    - GameObject player
    
    + void ChasePlayer()
}

class GameOverManager{
    - GameObject escapeTimerUI
    - TextMeshProUGUI timerText
    - GameManager gameManager
    - int timer
    - bool policeNear
    - bool routineRuns
    
    + IEnumerator EscapeTimer()
    - void OnTriggerEnter(Collider other)
    - void OnTriggerStay(Collider other)
    - void OnTriggerExit(Collider other)
}

class EnemyDespawnManager{
    - GameObject police
    - bool playerNear
    
    + IEnumerator DespawnCountDown()
    + void OnTriggerEnter(Collider other)
    + void OnTriggerExit(Collider other)
}

class CrashManager{
    - AuidoSource audioSource
    
    - void OnCollisionEnter(Collision col)
}
MonoBehaviour <|-- GameManager
MonoBehaviour <|-- MenuManager
MonoBehaviour <|-- CameraManager
MonoBehaviour <|-- MenuCameraManager
MonoBehaviour <|-- PlayerController
MonoBehaviour <|-- EnemySpawnManager
MonoBehaviour <|-- AgentScript
MonoBehaviour <|-- GameOverManager
MonoBehaviour <|-- EnemyDespawnManager
MonoBehaviour <|-- CrashManager
GameOverManager ---> GameManager

@enduml

```

