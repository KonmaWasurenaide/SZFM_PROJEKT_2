using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.AI;

public class AgentScript : MonoBehaviour
{
    [SerializeField] NavMeshAgent agent;
    [SerializeField] GameObject player;
    // Start is called before the first frame update
    void Start()
    {
        player = GameObject.Find("Player");
    }
    public void ChasePlayer()
    {
        if (player != null)
        {
            agent.SetDestination(player.transform.position);
        }
    }

    // Update is called once per frame
    void Update()
    {
        ChasePlayer();
    }
}
