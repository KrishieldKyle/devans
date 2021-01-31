import PropTypes from 'prop-types';

const SubmitButton = ({type, value, onClick, padding, margin, fontSize, fontWeight, borderRadius, border, background, cursor}) => {
    return (
        <input style={
                {
                    padding, 
                    margin, 
                    fontSize, 
                    fontWeight, 
                    borderRadius, 
                    border, 
                    background, 
                    cursor
                }
            } 
            type={type} 
            value={value} 
            onClick={onClick}/>
    )
}

SubmitButton.propTypes = {
    type: PropTypes.string,
    value: PropTypes.string.isRequired,
    onClick: PropTypes.func,
    padding: PropTypes.string,
    margin: PropTypes.string,
    fontSize: PropTypes.string,
    fontWeight: PropTypes.string,
    borderRadius: PropTypes.string,
    border: PropTypes.string,
    background: PropTypes.string,
    cursor: PropTypes.string,
    

}

SubmitButton.defaultProps = {
    type: 'button',
    padding: "5px 15px 5px",
    margin: "20px 10px 20px",
    fontSize: ".5em",
    fontWeight: "1000",
    borderRadius: "5px",
    border: "1px solid rgb(34, 126, 187)",
    background: "rgb(241, 241, 243)",
    cursor: "pointer"
}

export default SubmitButton
