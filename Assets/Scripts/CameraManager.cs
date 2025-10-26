using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class CameraManager : MonoBehaviour
{

    private GameObject player;
    [SerializeField] Vector3 offset;
    [SerializeField] Quaternion playerRotation;
    
    // Start is called before the first frame update
    void Start()
    {
        player = GameObject.Find("Player");
    }

    // Update is called once per frame
    void Update()
    {
        
        UpdateCamera();
    }

    private void UpdateCamera()
    {
        playerRotation = player.transform.rotation;

        transform.transform.position = player.transform.position + offset;
        transform.rotation = Quaternion.Euler(70, playerRotation.eulerAngles.y, 0);
    }
}
