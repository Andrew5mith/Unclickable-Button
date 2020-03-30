/*
* Andrew Smith
* 03/29/2020
* Test 2 Cant Click This
*/

package application;
	
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;


public class Main extends Application {
	
	//declare local variables
	private Stage window;
	private Scene titleScene, gameScene, gameOverScene;
	private TextField userName = new TextField();
	private Button unclickableButton;

	@Override
	public void start(Stage primaryStage) {
		try {
			
			//create a local variable for the stage
			window = primaryStage;
			
			//create title screen and add the header, body and footer
			BorderPane titlePane = new BorderPane();
			titlePane.setTop(getTitleHeader()); 
			titlePane.setCenter(getTitleBody());
			titlePane.setBottom(getTitleFooter());
			titleScene = new Scene(titlePane,500,500); //place the pane in the scene with specified dimensions
			window.setScene(titleScene); //set the initial scene in the stage
			window.show(); //show stage
			
			//create game screen where user trys to click the button
			Pane gamePane = new Pane();
			gamePane.getChildren().addAll(getMeanButton()); //add code created for the unclickable button on line 106
			//place button in center of pane
			unclickableButton.setTranslateX(250);
			unclickableButton.setTranslateY(250);	
			gameScene = new Scene(gamePane, 500, 500);
			
			//create the scene for game over image to appear
			BorderPane gameOverPane = new BorderPane();
			gameOverPane.setCenter(getGameOverImage());
			gameOverPane.setStyle("-fx-background-color: black");
			gameOverScene = new Scene(gameOverPane, 500, 500);

		} catch(Exception e) {
			e.printStackTrace();    //if exception occurs print stack trace 
		}
	}
	

	/*
	 * Found this idea on stackoverflow. I read that in some cases
	 * it is more convenient to use timeline over timer to negate
	 * having to add Platform.runlater() calls. Found documentation here: 
	 * https://docs.oracle.com/javafx/2/api/javafx/animation/Animation.html
	 * 
	 * This will change the button text every 5 seconds until 20 seconds 
	 * is reached and the scene is switched to the gameOver scene.
	 */
	public void changeButtonText(Button unclickableButton, Scene gameScene) {
	    Timeline timeline1 = new Timeline(new KeyFrame(Duration.seconds(5), e -> {    //creating a new time line
	    	unclickableButton.setText("Click Me!");                                   //changing the text of the button
	    }));
	    timeline1.play();                                                             // play the first timeline
	    Timeline timeline2 = new Timeline(new KeyFrame(Duration.seconds(10), e -> {  //each duration is set accordingly to switch the text after 5 seconds
	    	unclickableButton.setText("Too Slow!");
	    }));
	    timeline2.play();
	    Timeline timeline3 = new Timeline(new KeyFrame(Duration.seconds(15), e -> {
	    	unclickableButton.setText("You're Garbage!");
	    }));
	    timeline3.play();
	    Timeline timeline4 = new Timeline(new KeyFrame(Duration.seconds(20), e -> {
	    	window.setScene(gameOverScene);                                          //at 20 seconds switch to the gameOverScreen
	    }));
	    timeline4.play();
	}
	
	//create the unclickable button and place it in an HBox
	private HBox getMeanButton() {
		HBox buttonBox = new HBox(15);
		buttonBox.setAlignment(Pos.CENTER);   //center inside hbox
		
		
		unclickableButton = new Button("Click Me!");
		unclickableButton.addEventFilter(MouseEvent.MOUSE_MOVED, new EventHandler<MouseEvent>() { //mouse event to read movement of mouse
		    @Override
		    public void handle(MouseEvent mouseEvent) {
		    	
		    	//initialize x and y coordinate variables
		    	double x = 0, y = 0;
		    	//get the current position of button
		    	x = unclickableButton.getLayoutX();
		    	y = unclickableButton.getLayoutY();
		    	
		    	//increment the position by a random number 
		    	x += Math.random() * 400;
		    	y += Math.random() * 400;
		    	//jump away from border
		    	if(x >= 500 || x <= 0 || y >= 500 || y <= 0) {
		    		unclickableButton.setTranslateX(250);
		    		unclickableButton.setTranslateY(250);
		    	}
		    	else {
		    		//set the x and y coordinate with random value x and y
		    		unclickableButton.setTranslateX(x);
		    		unclickableButton.setTranslateY(y);
		    	}
		    				    }
		}); 
		unclickableButton.addEventFilter(MouseEvent.MOUSE_ENTERED_TARGET, new EventHandler<MouseEvent>() {   //I added this eventfilter so user cant click and drag mouse to land on button to click.
		    @Override
		    public void handle(MouseEvent mouseEvent) {
		    	
		    	//initialize x and y coordinate variables
		    	double x = 0, y = 0;
		    	//get the current position of button
		    	x = unclickableButton.getLayoutX();
		    	y = unclickableButton.getLayoutY();
		    	
		    	//increment the position by a random number
		    	x += Math.random() * 400;
		    	y += Math.random() * 400;
		    	//jump away from border if greater than or less than the width and height of pane. Since it's set at 500, x,y > 500 is used. 
		    	if(x >= 500 || x <= 0 || y >= 500 || y <= 0) { 
		    		unclickableButton.setTranslateX(250);
		    		unclickableButton.setTranslateY(250); 
		    	}
		    	else {
		    		//set the x and y coordinates with the new random numbers
		    		unclickableButton.setTranslateX(x); 
		    		unclickableButton.setTranslateY(y);
		    	}
		    				    }
		}); 
		
		buttonBox.getChildren().addAll(unclickableButton); // add button to buttonbox
		return buttonBox; 
	}
	//create welcome message
	private HBox getTitleHeader() {
		HBox titleHeader = new HBox(15);
		titleHeader.setPadding(new Insets(50,15,15,15));
		titleHeader.setStyle("-fx-font-size: 16pt;"); //set the font size of the header text 
		titleHeader.setAlignment(Pos.CENTER); 
		Label titleLabel = new Label("Welcome to the button clicking game!");
		titleHeader.getChildren().addAll(titleLabel);
		return titleHeader;
	}
	
	//add an hbox and place game over image inside
	private HBox getGameOverImage() {
		HBox hBox = new HBox(25);
		ImageView imageView = new ImageView(new Image("GameOver.jpg"));
		hBox.setAlignment(Pos.CENTER);
        imageView.setPreserveRatio(true); // the image is large so we are going to maintain the images aspect ratio and shrink it down to fit center
        imageView.setFitWidth(400);
        imageView.setFitHeight(300);
		hBox.getChildren().add(imageView); //add image to hbox
		return hBox;
	}
	
	//create username label and text field 
	private GridPane getTitleBody() {
		GridPane titleBody = new GridPane();
		titleBody.setAlignment(Pos.CENTER);
		titleBody.setHgap(5);
		titleBody.setVgap(5);
		titleBody.add(new Label("Username:"), 0, 0);
		titleBody.add(userName, 1, 0);
		return titleBody;
	}

	// creates hbox for the bottom of title screen and place play button. 
	private HBox getTitleFooter() {
		HBox titleFooter = new HBox();
		titleFooter.setPadding(new Insets(25,25,25,25));
		titleFooter.setAlignment(Pos.CENTER);
		Button playButton = new Button("Play");
		playButton.setMinWidth(75);
	    playButton.setOnAction(new EventHandler<ActionEvent>() { //handle click event 
	        @Override // Override the handle method
	        public void handle(ActionEvent e) {
	        	//check if username has been entered
	          if(userName.getLength() > 0) {
	        	  window.setScene(gameScene);    //if username entered set the gameScene and call the changeButtonText method to use timeline to change the text and scene for gameover
	        	  changeButtonText(unclickableButton, gameScene);
	          }
	          else {
	        	//this creates an alert box that informs users to enter a username to play; ok button dismisses
	        	Alert alert = new Alert(AlertType.INFORMATION);
	        	alert.setTitle("Inavlid Input"); //title of alert box
	        	alert.setHeaderText(null); //remove the header
	        	alert.setContentText("Please enter your username!"); //show message
	        	alert.showAndWait(); 
	          }
	        }
	      });
		titleFooter.getChildren().addAll(playButton); 

		return titleFooter;
	}
	
	public static void main(String[] args) {
		launch(args);

	}
}
