import React from 'react';
import Enzyme, { mount } from 'enzyme';
import Adapter from 'enzyme-adapter-react-16';
import ProjectList from '../ProjectList';
import ProjectTile from '../ProjectTile';

Enzyme.configure({ adapter: new Adapter() });

describe('Project list', () => {
  const props = {};

  const projectInfo = [
    {
      id: 1,
      projectName: 'test',
      projectIdentifier: 'TEST',
      description: 'test description',
      createdAt: '14-12-2019',
    },
  ];

  it('has a header', () => {
    expect(
      mount(<ProjectList {...props} data={projectInfo} />).find('h1').length
    ).toEqual(1);
  });

  it('renders a project tile', () => {
    const projectList = mount(<ProjectList {...props} data={projectInfo} />);
    expect(projectList.find(ProjectTile).length).toBeGreaterThan(0);
  });

  it('has no menus open by default', () => {
    const projectList = mount(<ProjectList {...props} data={projectInfo} />);
    expect(projectList.find('ol').length).toEqual(0);
  });

  it('opens a project menu when a project tile button is clicked', () => {
    const projectList = mount(<ProjectList {...props} data={projectInfo} />);
    projectList.find('button').simulate('click');
    expect(projectList.state('activeTile')).toEqual(1);
  });

  it('closes a project menu when a project tile button is clicked', () => {
    const projectList = mount(<ProjectList {...props} data={projectInfo} />);
    const button = projectList.find('button');
    button.simulate('click');
    button.simulate('click');
    expect(projectList.state('activeTile')).toEqual(null);
  });

});
