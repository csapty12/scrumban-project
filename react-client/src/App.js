import React from 'react';
import ReactDOM from 'react-dom';
import ProjectList from './ProjectList';
import Navbar from "./Navbar"
import style from './app.css';

const data = [
  {
    id: 1,
    projectName: 'project1',
    projectIdentifier: 'TEST1',
    description: 'test description',
    createdAt: '14-12-2019',
  },
  {
    id: 2,
    projectName: 'project2',
    projectIdentifier: 'TEST2',
    description: 'test description',
    createdAt: '14-12-2019',
  },
  {
    id: 3,
    projectName: 'project3',
    projectIdentifier: 'TEST3',
    description: 'test description',
    createdAt: '14-12-2019',
  },
  {
    id: 4,
    projectName: 'project4',
    projectIdentifier: 'TEST4',
    description: 'test description',
    createdAt: '14-12-2019',
  },
  {
    id: 5,
    projectName: 'project5',
    projectIdentifier: 'TEST5',
    description: 'test description',
    createdAt: '14-12-2019',
  },
];
const App = () => (
  <div>
    <Navbar />
    <ProjectList data={data} />
  </div>
);

export default App;
