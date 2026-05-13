# Physics Simulator
<!-- TOC -->
* [Physics Simulator](#physics-simulator)
    * [Update 02 - Electric Boogaloo](#update-02---electric-boogaloo)
    * [Update 01 - Hello World](#update-01---hello-world)
    * [To Do List:](#to-do-list)
<!-- TOC -->


### Update 02 - Electric Boogaloo

May 12, 2026

I've accomplished much of the setup work for this project. The application now displays a window, 
and I've built out the Vec2 and Mat3 classes. I'll be using 3x3 matrices to store the coordinate 
frames of my objects.

I felt proud when I managed to compute the product of any two 3x3 matrices programmatically. I pushed 
through a headache to do that.

I feel more comfortable with Java now that I've been fiddling with this project for a week. 
There is a lot of knowledge transfer between the fundamentals of one programming language and another. 
I expect to learn a lot about inheritance and polymorphism when I work on the Shape class and its children.
An understanding of the Java ecosystem will come with time. 

I'm thinking about how I want to handle collision. 

* My first thought was to simply create overloaded methods on each of the shapes. 
Circle.checkCollision(Square square), for instance.

* My second thought was to create a Collision class that can detect collisions 
between any two shapes.

I'll have to research this problem.

I want to start rendering things soon, so I'm going to work on the Shape and Circle classes, then try 
rendering a Circle on the screen.


### Update 01 - Hello World

May 5, 2026

My goals for this project are fourfold:

* First, I am learning to code in the Java programming language for an internship.
  I firmly believe digging deep into minutiae is the best way to become familiar
  with any domain, and I've found that I learn programming languages best by
  creating fun projects that keep me motivated. I have intentionally avoided
  libraries that would do most of the heavy lifting, so this project will
  require me to write a lot of structural code. I will get a good deal of practice
  writing Java - and that's the point.


* Second, I am becoming more and more fascinated with mathematics, especially
  linear algebra and calculus, and I feel compelled to explore the field's
  intersection with my primary collegiate focus, computer science. Graphics
  programming and physics seem natural places to begin. I feel that I have become
  an adept programmer in the last three years, and bolstering my computing skills
  with mathematics seems a good use of my summer.


* Third, I wish to practice writing professionally. I shall append updates to 
  this document whenever I finish significant changes to the codebase.


* Fourth, and finally, I have come to rely on the speedy accessibility of
  generative artificial intelligence, and I wish to be rid of the impulse to
  outsource my intellectual and creative thinking. This project will be solely
  my creation, and AI will have no part in producing the code in this repository.
  I have yet to decide if I shall permit myself to use AI to review the code and
  suggest changes; I am inclined to say that I shall not, but perhaps in later
  stages I will allow it.

### To Do List:

1) ~~Set up project dependencies~~
2) ~~Get a window open (using GLFW)~~
3) ~~Create OpenGL context~~
4) ~~Create NanoVG context~~
5) Draw a shape
6) Structure simulation loop
    * delta time
    * multithreading?
7) Define core data structures
    * physics bodies
    * position
    * orientation
    * velocity
    * mass
    * shape
8) Math library
    * ~~Vec2~~
    * ~~Mat3~~
    * ~~transform operations~~
        * ~~translation~~
        * ~~rotation~~
        * ~~scaling~~
    * linear interpolation
9) Kinematics
    * gravity
    * motion
    * integration
10) Collision detection
    * point
    * line
    * triangle
    * circle
    * quad
    * n-gon
11) Collision response
    * impulse resolution
    * restitution
12) Mouse interaction
    * get mouse position
    * object spawning
13) UI (using NanoVG)
    * object count
    * FPS
    * gravity toggle
