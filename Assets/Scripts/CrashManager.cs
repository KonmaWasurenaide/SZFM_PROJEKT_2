using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class CrashManager : MonoBehaviour
{
    private AudioSource audioSource;
    // Start is called before the first frame update
    void Start()
    {
        audioSource = gameObject.GetComponent<AudioSource>();
    }


    private void OnCollisionEnter(Collision col)
    {
        if (!col.gameObject.CompareTag("Ground"))
        {
            audioSource.Play();
        }
    }

    // Update is called once per frame
    void Update()
    {
        
    }
}
