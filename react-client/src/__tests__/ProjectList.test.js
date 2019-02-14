import React from 'react';
import Enzyme, { mount } from 'enzyme';
import Adapter from 'enzyme-adapter-react-16';
import ProjectList from '../ProjectList';
import ProjectTile from '../ProjectTile';
import ModalDialog from "../modalDialog/ModalDialog"

Enzyme.configure({ adapter: new Adapter() });
beforeAll(() => {
  global.fetch = jest.fn();
  //window.fetch = jest.fn(); if running browser environment
});
describe('Project list', () => {
  const props = {};

  it('has a header', () => {
    expect(
      mount(<ProjectList {...props} />).find('h1').length
    ).toEqual(1);
  });
  it('has no dialog open by default', () => {
    const modal = mount(<ModalDialog {...props} />);
    expect(modal.find('#myModal').length).toEqual(0);
  }

//   // it('renders a project tile', () => {
//   //   const projectList = mount(<ProjectList {...props} />);
//   //   expect(projectList.find(ProjectTile).length).toBeGreaterThan(0);
//   // });

//   it('has no menus open by default', () => {
//     const projectList = mount(<ProjectList {...props} />);
//     expect(projectList.find('ol').length).toEqual(0);
//   });

//   // it('opens a project menu when a project tile button is clicked', () => {
//   //   const projectList = mount(<ProjectList {...props}/>);
//   //   projectList.find('button').simulate('click');
//   //   expect(projectList.state('activeTile')).toEqual(1);
//   // });

//   // it('closes a project menu when a project tile button is clicked', () => {
//   //   const projectList = mount(<ProjectList {...props} />);
//   //   const button = projectList.find('button');
//   //   button.simulate('click');
//   //   button.simulate('click');
//   //   expect(projectList.state('activeTile')).toEqual(null);
//   // });

});
