using System.Collections;
using System.Collections.Generic;
using UnityEditor;
using UnityEngine;
using UnityEngine.EventSystems;

public class PlayerController : MonoBehaviour
{

    [SerializeField] float motorForce = 100f;
    [SerializeField] float brakeForce = 1000f;
    [SerializeField] float handBrakeForce = 100000f;
    [SerializeField] float maxMotorTorque;
    //[SerializeField] float maxSteerAngle = 30f;

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
    //private float currentBrakeForce;
    private bool handBrake;
    private Rigidbody rb;



    [SerializeField] AnimationCurve steeringCurve;
    
    private float speed;


    [SerializeField] float slipAngle;
    [SerializeField] float backSlipAngle;
    [SerializeField] float brakeInput;
    [SerializeField] bool isBraking;

    private void Start()
    {
        rb = gameObject.GetComponent<Rigidbody>();
        rb.centerOfMass = new Vector3(0, -0.7f, 0.2f);
    }

    private void Update()
    {
        speed = rb.velocity.magnitude;
        GetInput();
        HandleMotor();
        HandleSteering();
        UpdateWheels();
        ApplyBreaking();
        ApplyHandBreak();
        Debug.Log(rb.velocity);
    }

    private void GetInput()
    {
        horizontalInput =Input.GetAxis("Horizontal");
        verticalInput = Input.GetAxis("Vertical");
        handBrake = Input.GetKey(KeyCode.Space);
        slipAngle = Vector3.Angle(transform.forward, rb.velocity - transform.forward);
        backSlipAngle = Vector3.Angle(-transform.forward, rb.velocity + transform.forward);
        if (slipAngle < 120f)
        {
            if (verticalInput < 0)
            {
                brakeInput = Mathf.Abs(verticalInput);
                isBraking = true;
            }
            else
            {
                brakeInput = 0;
                isBraking = false;
            }
        }
        else if (backSlipAngle < 120f)
        {
            if (verticalInput > 0)
            {
                brakeInput = Mathf.Abs(verticalInput);

                isBraking = true;
            }
            else
            {
                brakeInput = 0;
                isBraking = false;
            }
        }
        else
        {
            brakeInput = 0;
            isBraking = false;
        }

    }



    private void HandleMotor()
    {
        
           backLeftWheelCollider.motorTorque = verticalInput * motorForce;
           backRightWheelCollider.motorTorque = verticalInput * motorForce;
        
        

        


    }

    private void ApplyHandBreak()
    {
        if (handBrake)
        {
            
            backLeftWheelCollider.brakeTorque = handBrakeForce*0.7f;
            backRightWheelCollider.brakeTorque = handBrakeForce * 0.7f;
            frontRightWheelCollider.brakeTorque = handBrakeForce * 0.1f;
            frontLeftWheelCollider.brakeTorque = handBrakeForce * 0.1f;
            isBraking = true;
        }
        else
        {
            isBraking = false;
        }
    }


   

    private void ApplyBreaking()
    {
        float totalBrakeForce = 0f;

        if (isBraking)
        {
            // rendes fékezés inputból
            totalBrakeForce = brakeInput * brakeForce;
        }
        else if (Mathf.Abs(verticalInput) < 0.1f && !handBrake)
        {
            // motorfék, ha nincs gáz
            totalBrakeForce = brakeForce * 0.2f; // kb. 20% erõ
        }


        frontRightWheelCollider.brakeTorque = totalBrakeForce * 0.2f;
        frontLeftWheelCollider.brakeTorque = totalBrakeForce * 0.2f;
        backLeftWheelCollider.brakeTorque = totalBrakeForce * 0.8f;
        backRightWheelCollider.brakeTorque = totalBrakeForce * 0.8f;
    }

    private void HandleSteering()
    {


        currentSteerAngle = horizontalInput* steeringCurve.Evaluate(speed);
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
