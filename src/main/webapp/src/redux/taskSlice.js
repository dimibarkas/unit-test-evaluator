import {createSlice} from "@reduxjs/toolkit";

export const taskSlice = createSlice({
    name: 'task',
    initialState: {
        selection: null,
    },
    reducers: {
        setSelection: (state, action) => {
            state.selection = action.payload
        }
    }
})

export const {setSelection} = taskSlice.actions

export default taskSlice.reducer