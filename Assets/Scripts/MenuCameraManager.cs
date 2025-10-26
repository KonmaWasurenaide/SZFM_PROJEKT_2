using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class MenuCameraManager : MonoBehaviour
{
    [SerializeField] Vector2 min;
    [SerializeField] Vector2 max;
    [SerializeField] Vector2 yRotationRange;
    [SerializeField] float lerpSpeed=0.5f;
    [SerializeField] float rotationSpeed = 0.5f;
    private Vector3 newPosition;
    private Quaternion newRotation;
    // Start is called before the first frame update
    private void Awake()
    {
        newPosition = transform.position;
        newRotation= transform.rotation;
    }

    // Update is called once per frame
    private void FixedUpdate()
    {

        UpdateMainMenuCamera();



    }

    private void GetNewPosition()
    {
        var xPoz = Random.Range(min.x, max.x);
        var zPoz = Random.Range(min.y, max.y);
        newPosition = new Vector3(xPoz,70.3f ,zPoz);
        newRotation = Quaternion.Euler(42.6f,Random.Range(yRotationRange.x,yRotationRange.y),0);
    }

    private void UpdateMainMenuCamera()
    {
        transform.position = Vector3.MoveTowards(transform.position, newPosition, Time.deltaTime * lerpSpeed);
        transform.rotation = Quaternion.Lerp(transform.rotation, newRotation, Time.deltaTime * rotationSpeed);

        if (Vector3.Distance(transform.position, newPosition) < 1f)
        {
            GetNewPosition();
        }
    }
}



