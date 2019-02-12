import React from 'react';
import Enzyme, { mount } from 'enzyme';
import Adapter from 'enzyme-adapter-react-16';
import App from '../App';

Enzyme.configure({ adapter: new Adapter() });

describe('test', () => {
  test('renders', () => {
    const wrapper = mount(<App />);
    expect(wrapper.exists()).toBe(true);
  });
});
