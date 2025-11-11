using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class EnemyDespawnManager : MonoBehaviour
{
    
    [SerializeField] GameObject police;
    [SerializeField] bool playerNear;
    // Start is called before the first frame update
    void Start()
    {
        
        StartCoroutine(DespawnCountDown());
    }

    // Update is called once per frame
    void Update()
    {
        
    }


    public IEnumerator DespawnCountDown()
    {
        //int random = Random.Range(0, enemyManager.spawnPositions.Length);
        int timer = 10;
        while (true)
        {
            yield return new WaitForSeconds(1);
            timer--;


            if (playerNear)
            {
                break;
            }
            else if (timer==0)
            {
                //police.transform.position = enemyManager.spawnPositions[random].position;
                Destroy(police);
                StartCoroutine(DespawnCountDown());
            }
        }
        StopCoroutine(DespawnCountDown());


    }

    public void OnTriggerEnter(Collider other)
    {
        if (other.gameObject.CompareTag("Player"))
        {
            
            playerNear = true;
        }
    }

    

    public void OnTriggerExit(Collider other)
    {
        if (other.gameObject.CompareTag("Player"))
        {
            StartCoroutine(DespawnCountDown());
            playerNear = false;
        }
    }
}
