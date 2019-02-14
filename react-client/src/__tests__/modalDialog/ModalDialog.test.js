import React from 'react';
import Enzyme, { mount } from 'enzyme';
import Adapter from 'enzyme-adapter-react-16';
import ModalDialog from '../../modalDialog/ModalDialog';

Enzyme.configure({ adapter: new Adapter() });

describe("Modal dialog", ()=>{
    const props = {}

    it('renders without error', () => {
    expect(mount(<ModalDialog {...props} />).length).toEqual(1);
    });
    it("contains a header", ()=>{
        const wrapper=mount(<ModalDialog {...props} />);
        expect(wrapper.find('.modalHeader').length).toEqual(1);
    })
    
    describe("modal form", ()=>{
        it("contains a form", ()=>{
                const wrapper=mount(<ModalDialog {...props} />);
                expect(wrapper.find('form').length).toEqual(1);
        })
        it("contains the project name", ()=>{
                const wrapper=mount(<ModalDialog {...props} />);
                expect(wrapper.find('#projectName').length).toEqual(1);
        })
        it("contains the project description", ()=>{
                const wrapper=mount(<ModalDialog {...props} />);
                expect(wrapper.find('#description').length).toEqual(1);
        })
        it("contains a cancel and create button", ()=>{
                const wrapper=mount(<ModalDialog {...props} />);
                expect(wrapper.find('.projectButton').length).toEqual(2);
        })
    })
})