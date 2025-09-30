using System.Collections;
using System.Collections.Generic;
using UnityEditor;
using UnityEngine;

public class PlayerController : MonoBehaviour
{

    [SerializeField] float motorForce = 100f;
    [SerializeField] float brakeForce = 1000f;
    [SerializeField] float maxSteerAngle = 30f;

    public WheelCollider frontLeftWheelCollider;
    public WheelCollider backLeftWheelCollider;
    public WheelCollider frontRightWheelCollider;
    public WheelCollider backRightWheelCollider;

    public Transform frontLeftWheelTransform;
    public Transform backLeftWheelTransform;
    public Transform frontRightWheelTransform;
    public Transform backRightWheelTransform;

    private float horizontalInput;
    private float verticalInput;
    private float currentSteerAngle;
    private float currentBrakeForce;
    private bool isBreaking;
    private Rigidbody rb;

    private void Start()
    {
        rb = gameObject.GetComponent<Rigidbody>();
        rb.centerOfMass = new Vector3(0, -0.5f, 0.3f);
    }

    private void Update()
    {
        GetInput();
        HandleMotor();
        HandleSteering();
        UpdateWheels();
    }

    private void GetInput()
    {
        horizontalInput =Input.GetAxis("Horizontal");
        verticalInput = Input.GetAxis("Vertical");
        isBreaking = Input.GetKey(KeyCode.Space);
    }

    private void HandleMotor()
    {
        frontLeftWheelCollider.motorTorque = verticalInput * motorForce;
        frontRightWheelCollider.motorTorque = verticalInput * motorForce;

        currentBrakeForce = isBreaking ? brakeForce : 0f;
        ApplyBreaking();
    }

    private void ApplyBreaking()
    {
        frontRightWheelCollider.brakeTorque = currentBrakeForce;
        frontLeftWheelCollider.brakeTorque = currentBrakeForce;
        backLeftWheelCollider.brakeTorque = currentBrakeForce;
        backRightWheelCollider.brakeTorque = currentBrakeForce;
    }

    private void HandleSteering()
    {
        currentSteerAngle = maxSteerAngle * horizontalInput;
        frontLeftWheelCollider.steerAngle = currentSteerAngle;
        frontRightWheelCollider.steerAngle = currentSteerAngle;
    }


    private void UpdateSingleWheel(WheelCollider wheelCollider,Transform wheelTransform)
    {
        Vector3 pos;
        Quaternion rot;
        wheelCollider.GetWorldPose(out pos, out rot);
        wheelTransform.position = pos;
        wheelTransform.rotation = rot;

    }

    private void UpdateWheels()
    {
        UpdateSingleWheel(frontLeftWheelCollider, frontLeftWheelTransform);
        UpdateSingleWheel(frontRightWheelCollider, frontRightWheelTransform);
        UpdateSingleWheel(backLeftWheelCollider, backLeftWheelTransform);
        UpdateSingleWheel(backRightWheelCollider, backRightWheelTransform);
    }
    /*[SerializeField] float horizontalInput;
    [SerializeField] float verticalInput;
    [SerializeField] float speed;
    [SerializeField] float turningSpeed;
    private Rigidbody rb;

    // Start is called before the first frame update
    void Start()
    {
        rb= gameObject.GetComponent<Rigidbody>();
        
    }

    // Update is called once per frame
    void Update()
    {
        horizontalInput = Input.GetAxis("Horizontal");
        verticalInput = Input.GetAxis("Vertical");

        transform.Translate(Vector3.forward*verticalInput*speed*Time.deltaTime);
        

        if (verticalInput != 0)
        {
            if (verticalInput > 0)
            {
                transform.Rotate(Vector3.up, horizontalInput * turningSpeed * Time.deltaTime);
            }
            if (verticalInput < 0)
            {
                transform.Rotate(Vector3.down, horizontalInput * turningSpeed * Time.deltaTime);
            }
        }

    }*/
}
