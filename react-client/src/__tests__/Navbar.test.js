import React from 'react';
import Enzyme, { mount } from 'enzyme';
import Adapter from 'enzyme-adapter-react-16';
import Navbar from '../Navbar';

Enzyme.configure({ adapter: new Adapter() });

describe('navbar', () => {
  it('renders', () => {
    const wrapper = mount(<Navbar />);
    expect(wrapper.exists()).toBe(true);
  });
  it("displays navbar with 3 links", ()=>{
       const wrapper = mount(<Navbar />);
       expect(wrapper.find("a").length).toEqual(3);
  })
});