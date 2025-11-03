using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class EnemySpawnManager : MonoBehaviour
{

    GameManager gameManager;
    [SerializeField] GameObject police;
    [SerializeField] int starCount;
    [SerializeField] int policeCount;
    public Transform[] spawnPositions;
    // Start is called before the first frame update
    void Start()
    {
        gameManager = GetComponent<GameManager>();
    }

    // Update is called once per frame
    void Update()
    {
       SpawnPolice();

        
    }

    public void SpawnPolice()
    {
        starCount = GameObject.FindGameObjectsWithTag("star").Length;
        policeCount = GameObject.FindGameObjectsWithTag("Police").Length;
        if (starCount != policeCount)
        {
            int randomPos = Random.Range(0, spawnPositions.Length);
            Instantiate(police, spawnPositions[randomPos].position, police.transform.rotation);
        }
    }
}
