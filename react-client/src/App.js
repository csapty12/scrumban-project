import React from 'react';
import { BrowserRouter as Router, Route } from 'react-router-dom';
import ProjectList from './ProjectList';
import Navbar from './Navbar';

const App = () => (
  <Router>
    <div className="App">
      <Navbar />
      <Route exact path="/dashboard" component={ProjectList} />
    </div>
  </Router>
);

export default App;
