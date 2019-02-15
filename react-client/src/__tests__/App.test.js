import React from 'react';
import Enzyme, { mount } from 'enzyme';
import Adapter from 'enzyme-adapter-react-16';
import App from '../App';
import Navbar from "../Navbar"
import { MemoryRouter } from 'react-router';
import ReactDOM from 'react-dom';
import ProjectList from "../ProjectList";

Enzyme.configure({ adapter: new Adapter() });
beforeAll(() => {
  // global.fetch = jest.fn();
  window.fetch = jest.fn(); //if running browser environment
});
describe('entry point to the application', () => {
  it('renders', () => {
    const wrapper = mount(<MemoryRouter initialEntries={[ '/' ]}>
      <App/>
    </MemoryRouter>);
    expect(wrapper.exists()).toBe(true);
  });
  it('renders navbar', () => {
    const wrapper = mount(<Navbar />);
    expect(wrapper.exists()).toBe(true);
  });
  it("valid path should not redirect to 404", ()=>{
const wrapper = mount(
    <MemoryRouter initialEntries={[ '/dashboard' ]}>
      <App/>
    </MemoryRouter>
  );
  expect(wrapper.find(ProjectList)).toHaveLength(1);
  })

});
