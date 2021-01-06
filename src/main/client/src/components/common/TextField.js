import PropTypes from 'prop-types';

import "../../assets/css/common/TextField.css";


const TextField = ({placeholder, type, name, value, onChange, error, disabled}) => {
    return (
        <div id="textfield-container">
            <label htmlFor={placeholder}>
                <input className={error ? "invalid-input" : ""} type={type} name={name} placeholder=" " value={value} onChange={onChange} disabled={disabled}/>
                <span className={error ? "invalid-input" : ""}>{placeholder}</span>
            </label>
            <div className="error-div">
                <span className="error-span">{error}</span>
            </div>
        </div>
    )
}

TextField.propTypes = {
    name: PropTypes.string.isRequired,
    placeholder: PropTypes.string.isRequired,
    value: PropTypes.string.isRequired,
    error: PropTypes.string,
    type: PropTypes.string,
    onChange: PropTypes.func.isRequired,
    disabled: PropTypes.bool
}

TextField.defaultProps = {
    type: 'text'
}

export default TextField
