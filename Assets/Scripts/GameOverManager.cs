using System.Collections;
using System.Collections.Generic;
using System.Threading;
using TMPro;
using UnityEngine;

public class GameOverManager : MonoBehaviour
{
    [SerializeField] GameObject escapeTimerUI;
    [SerializeField] TextMeshProUGUI timerText;
    GameManager gameManager;
    int timer = 7;
    bool policeNear;
    bool routineRuns;
    
    // Start is called before the first frame update
    void Start()
    {
        escapeTimerUI.SetActive(false);
        gameManager = GameObject.Find("GameManager").GetComponent<GameManager>();
    }
    IEnumerator EscapeTimer()
    {
        routineRuns = true;
        Debug.Log("corut");
        timer = 7;
        timerText.SetText("Time Left: "+timer.ToString());
        escapeTimerUI.SetActive(true);
        while (policeNear)
        {
            yield return new WaitForSeconds(1);
            timer--;
            timerText.SetText("Time Left: " + timer.ToString());
            
            if (policeNear&& timer==0)
            {
                gameManager.GameOver();
                escapeTimerUI.SetActive(false);
            }
            
        }
        routineRuns= false;
        StopCoroutine(EscapeTimer());

        

    }
    /*
    public void EscapTimer()
    {
        timer = 5;
        for(int i = 0; i < 5; i++)
        {
            
            timer--;
            timerText.SetText("Time Left: " + timer.ToString());
            if (policeNear = false)
            {
                break;
            }
            if (timer == 0)
            {
                gameManager.GameOver();
            }
            yield return new WaitForSeconds(1);
        }
    }
    */

    private void OnTriggerEnter(Collider other)
    {
        if (other.CompareTag("Police")&& policeNear==false && routineRuns==false)
        {
            
            policeNear = true;
            StartCoroutine(EscapeTimer());
            Debug.Log("entered");
        }
        
        
    }
    /*
    private void OnTriggerStay(Collider other)
    {
        if (other.CompareTag("Police"))
        {
            policeNear = true;
        }
    }
    */

    private void OnTriggerExit(Collider other)
    {
        if (other.CompareTag("Police"))
        {
           
            policeNear = false;
            escapeTimerUI.SetActive(false);
            //StopCoroutine(EscapeTimer());
            
        }
    }
    

    // Update is called once per frame
    void Update()
    {
        /*
        if (timer == 0 && policeNear)
        {
            gameManager.GameOver();
            escapeTimerUI.SetActive(false);
        }
        */
        
    }
}
