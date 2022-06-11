<div align="center">
    <img src="images/logo.png" alt="Logo" width="210" height="160">

  <h3 align="center">Yfi Tops</h3>

  <p align="center">
    The Yfi Tops Project is a school practical project that aims to extend our skills on android app development and team work. 
    <br />
<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#architecture">Architecture</a></li>
    <li><a href="#project structure">Project Structure</a></li>
    <li><a href="#firebase data structure">Firebase Data Structure</a></li>
    <li><a href="#license">License</a></li>
    <li><a href="#contact">Contact</a></li>
  </ol>
</details>




<!-- ABOUT THE PROJECT -->

## About The Project

![](images/screen_1.jpg)

There are many great README templates available on GitHub; however, I didn't find one that really suited my needs so I created this enhanced one. I want to create a README template so amazing that it'll be the last one you ever need -- I think this is it.

Here's why:
* Your time should be focused on creating something amazing. A project that solves a problem and helps others
* You shouldn't be doing the same tasks over and over like creating a README from scratch
* You should implement DRY principles to the rest of your life :smile:

Of course, no one template will serve all projects since your needs may be different. So I'll be adding more in the near future. You may also suggest changes by forking this repo and creating a pull request or opening an issue. Thanks to all the people have contributed to expanding this template!

Use the `BLANK_README.md` to get started.

<p align="right">(<a href="#top">back to top</a>)</p>

### Built With

* [Jetpack Compose](https://developer.android.com/jetpack/compose)
* [ExoPlayer](https://exoplayer.dev)
* [Firestore](https://firebase.google.com/docs/firestore)
* [Firebase Storage](https://firebase.google.com/docs/storage)
* [Glide](https://github.com/bumptech/glide)

<p align="right">(<a href="#top">back to top</a>)</p>



<!-- GETTING STARTED -->

## Getting Started

This is an example of how you may give instructions on setting up your project locally.
To get a local copy up and running follow these simple example steps.

### Prerequisites

This is an example of how to list things you need to use the software and how to install them.
* npm
  ```sh
  npm install npm@latest -g
  ```

### Installation

_Below is an example of how you can instruct your audience on installing and setting up your app. This template doesn't rely on any external dependencies or services._

1. Get a free API Key at [https://example.com](https://example.com)
2. Clone the repo
   ```sh
   git clone https://github.com/your_username_/Project-Name.git
   ```
3. Install NPM packages
   ```sh
   npm install
   ```
4. Enter your API in `config.js`
   ```js
   const API_KEY = 'ENTER YOUR API';
   ```

<p align="right">(<a href="#top">back to top</a>)</p>



<!-- USAGE EXAMPLES -->
## Usage

Use this space to show useful examples of how a project can be used. Additional screenshots, code examples and demos work well in this space. You may also link to more resources.

_For more examples, please refer to the [Documentation](https://example.com)_

<p align="right">(<a href="#top">back to top</a>)</p>

<!-- ARCHITECTURE -->

## Architecture

The general architecture of the application is shown below.

![architecture](images/architecture.png)

Our application consists of four main modules :
- **Jetpack compose** : it constitutes the entire graphical interface of the application, in which the components constituting the interface and the navigation between them are defined.

- **ViewModels** : these take care of updating the graphical interface dynamically when the livedata change. They are also used as an abstraction layer to communicate with the music service connector. In our case, our ViewModels use (wrap) the livedata contained in the firebase repository.

- **ExoPlayer**: This module takes care of the media playback part, allowing our songs to be played, keeping playback in the background and offering a miniplayer in the notification bar. This component in turn is divided into :
  - **Music Service Connector**: this takes care of offering other components/modules of the system all those methods and variables to be able to manage the player.
  - **Music Service**: takes care of initialising and preparing everything necessary for multimedia playback.
  - **Callbacks**: takes care of performing actions based on specific events.
  - **Converters**: take care of converting our templates into a format understandable by the player.
  - **Notification:** takes care of managing the notification system.

- **Firebase**: is used to save our music playlists, in particular **Firebase Storage** is used to save all .mp3 files, album images and individual song images. While **Firebase Datastore**, which is a NoSQL database, contains all the metadata of the playlists/songs, such as the title, author, image and link to the .mp3 file (contained in the Storage).

<!-- PROJECT STRUCTURE-->

## Project Structure

Below is shown the structuring and description of the different folders of the Android project.

![tree_structure](images/folders_tree.png)

<p align="right">(<a href="#top">back to top</a>)</p>

<!-- FIREBASE DATA STRUCTURE-->

## Firebase Data Structure

The image below shows how the data are organised on Firebase.

![data_structure](images/data_structure.png)

<p align="right">(<a href="#top">back to top</a>)</p>

<!-- LICENSE -->

## License

Distributed under the MIT License. See `LICENSE` for more information.

<p align="right">(<a href="#top">back to top</a>)</p>



<!-- CONTACT -->

## Contact

[Alessandro Parrino](https://github.com/Alessandro-AP) - alessandro.parrino@heig-vd.ch <br>
[Daniel Sciarra](https://github.com/DS-Daniel) - daniel.sciarra@heig-vd.ch <br>
[Wilfried Karel Ngueukam Djeuda](https://github.com/wilfried01) - wilfriedkarel.ngueukamdjeuda@heig-vd.ch

<p align="right">(<a href="#top">back to top</a>)</p>