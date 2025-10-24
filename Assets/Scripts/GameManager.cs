using System.Collections;
using System.Collections.Generic;
using TMPro;
using UnityEngine;
using UnityEngine.SceneManagement;

public class GameManager : MonoBehaviour
{

    [SerializeField] TextMeshProUGUI scoreText;
    [SerializeField] TextMeshProUGUI highScoreText;
    [SerializeField] TextMeshProUGUI timerText;
    [SerializeField] GameObject pauseMenu;

    [SerializeField] Animator pauseMenuAnimator;

    [SerializeField] GameObject gameOverMenu;

    [SerializeField] Animator gameOverMenuAnimator;

    private float actualTime = 0;
    public float score;
    public bool gameOver;
    public bool isPaused;
    // Start is called before the first frame update
    void Start()
    {

        gameOver = false;
        isPaused = false;
    }

    // Update is called once per frame
    void Update()
    {
        
        if (gameOver == false)
        {
            UpdateTimer();
            UpdateScore();
        }


        PauseManager();
        
        //forTesting
        GameOverTester();

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

    public void SaveHighScore(float score)
    {
        float highScore = PlayerPrefs.GetFloat("HighScore", 0);
        if (score > highScore)
        {
            PlayerPrefs.SetFloat("HighScore", Mathf.Floor(score));
            PlayerPrefs.Save();
        }
    }
    public float LoadHighScore()
    {
        return PlayerPrefs.GetFloat("HighScore", 0);
    }

    public void Restart()
    {
        isPaused = false;
        Time.timeScale = 1;
        SceneManager.LoadScene(SceneManager.GetActiveScene().name);
    }

    public void Pause()
    {
        pauseMenuAnimator.SetBool("IsOpen",true);
        pauseMenuAnimator.SetBool("IsClose",false);
        Time.timeScale = 0;
        isPaused = true;
    }


    public void Resume()
    {      
        pauseMenuAnimator.SetBool("IsOpen", false);
        pauseMenuAnimator.SetBool("IsClose", true);
        Time.timeScale = 1;
        isPaused = false;

    }

    public void PauseManager()
    {
        if (Input.GetKeyUp(KeyCode.Escape) && !gameOver)
        {
            if (isPaused == false)
            {
                Pause();
            }
            else if (isPaused == true)
            {
                Resume();
            }
            else
            {
                isPaused = !isPaused;
            }

        }
    }
    //forTesting
    public void GameOverTester()
    {
        if(Input.GetKey(KeyCode.Q))
        {
            gameOver = true;
            GameOver();
        }
    }

// ellenorizni hogy ne tudd megnyitni a pause menut kozbe
    public void GameOver()
    {
        SaveHighScore(Mathf.Floor(score));
        highScoreText.SetText("HighScore: " + LoadHighScore().ToString());
        gameOverMenuAnimator.SetBool("IsOpen", true);
        gameOverMenuAnimator.SetBool("IsClose", false);
        Time.timeScale = 0;

    }

    public void QuitToMainMenu()
    {
        SceneManager.LoadScene("Menu");
        Time.timeScale = 1;
    }
}
