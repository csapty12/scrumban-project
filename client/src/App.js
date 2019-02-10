import React from 'react';
import ProjectList from './ProjectList';

const data = [
  {
    id: 1,
    projectName: 'test',
    projectIdentifier: 'TEST',
    description: 'test description',
    createdAt: '14-12-2019',
  },
];
const App = () => (
  <div>
    <ProjectList data={data} />
  </div>
);

export default App;
