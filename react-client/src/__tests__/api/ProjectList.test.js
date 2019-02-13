import React from 'react';
import Enzyme, { mount, shallow } from 'enzyme';
import Adapter from 'enzyme-adapter-react-16';
import {fetchAllProjects} from "../../api/ProjectList"
import ProjectList from "../../ProjectList";
Enzyme.configure({ adapter: new Adapter() });

beforeAll(() => {
  global.fetch = jest.fn();
  //window.fetch = jest.fn(); if running browser environment
});

describe('fetch all projects', () => {
    let wrapper;
beforeEach(() => {
   wrapper = shallow(<ProjectList />, { disableLifecycleMethods: true });
});
afterEach(() => {
   wrapper.unmount();
});

it("can fetch all projects with the correct data", ()=>{
    const spyDidMount = jest.spyOn(ProjectList.prototype,"componentDidMount");
    fetch.mockImplementation(() => {
        return Promise.resolve({
            status: 200,
            json: () => {
                return Promise.resolve([
                    {  
                    "id":13,
                    "projectName":"sdfghjkytfugioi",
                    "projectIdentifier":"sdfghjk",
                    "description":"fghjk",
                    "createdAt":"08-02-2019",
                    "currentTicketNumber":0
                    }
                ]);
            }
        });
    });
    const didMount = wrapper.instance().componentDidMount(); 
    expect(spyDidMount).toHaveBeenCalled();
})
  it("sets an error when the fetch fails", ()=>{
     
  })
})
