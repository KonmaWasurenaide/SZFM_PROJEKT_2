using System.Collections;
using System.Collections.Generic;
using TMPro;
using UnityEngine;

public class GameManager : MonoBehaviour
{

    [SerializeField] TextMeshProUGUI scoreText;
    [SerializeField] TextMeshProUGUI timerText;
    private float actualTime = 0;
    public float score;
    public bool gameOver;
    // Start is called before the first frame update
    void Start()
    {
        gameOver = false;
    }

    // Update is called once per frame
    void Update()
    {
        

        if (gameOver == false)
        {
            UpdateTimer();
            UpdateScore();
        }
    }

    public void UpdateScore()
    {
        score += 10*Time.deltaTime;
        scoreText.text="Score: "+ Mathf.Floor(score);
    }

    public void UpdateTimer()
    {
        actualTime += Time.deltaTime;
        int minutes = Mathf.FloorToInt(actualTime / 60);
        int seconds = Mathf.FloorToInt(actualTime % 60);
        timerText.text = string.Format("{0:00}:{1:00}", minutes, seconds);
    }
}
