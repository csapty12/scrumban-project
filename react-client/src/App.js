import React from 'react';
import ReactDOM from 'react-dom';
import ProjectList from './ProjectList';
import Navbar from "./Navbar"
import style from './app.css';
import { BrowserRouter as Router, Route } from "react-router-dom";

const App = () => (
  <Router>
    <div className="App">
      <Navbar />
      <Route exact path="/dashboard" component={ProjectList} />
    </div>
  </Router>
);

export default App;
