$('#select-task-type').on('change', function(){
    let typeId = $(this).val()
    axios.post('/newTask/typeSelect?typeId=' + typeId)
        .then(resp => {
            console.log(resp)
            document.open()
            document.write(resp.data)
            document.close()
            $('#select-task-type').val(typeId)
        })
        .catch(err => {
            console.log(err)
        })
})
