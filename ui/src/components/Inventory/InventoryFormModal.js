import * as Yup from 'yup'
import Button from '@material-ui/core/Button'
import Checkbox from '@material-ui/core/Checkbox'
import Dialog from '@material-ui/core/Dialog'
import DialogActions from '@material-ui/core/DialogActions'
import DialogContent from '@material-ui/core/DialogContent'
import DialogTitle from '@material-ui/core/DialogTitle'
import FormControlLabel from '@material-ui/core/FormControlLabel'
import Grid from '@material-ui/core/Grid'
import { MenuItem } from '@material-ui/core'
import moment from 'moment'
import React from 'react'
import TextField from '../Form/TextField'
import { Field, Form, Formik } from 'formik'


class InventoryFormModal extends React.Component {
  FieldValidation = Yup.object().shape({
    name: Yup.string()
      .required('Required'),
    productType: Yup.string()
      .required('Required'),
    averagePrice: Yup.number()
      .min(0),
    amount: Yup.number()
      .min(0),
    unitOfMeasurement: Yup.string()
      .required('Required'),
  })
  render() {
    const {
      formName,
      handleDialog,
      handleInventory,
      title,
      initialValues,
      currProducts,
      units
    } = this.props
    return (
      <Dialog
        open={this.props.isDialogOpen}
        maxWidth='sm'
        fullWidth={true}
        onClose={() => { handleDialog(false) }}
      >
        <Formik
          initialValues={initialValues}
          validationSchema={this.FieldValidation}
          onSubmit={values => {
            values.neverExpires = document.getElementById('neverExpires').checked
            values.bestBeforeDate = new moment(values.bestBeforeDate).toJSON()
            handleInventory(values)
            handleDialog(true)
          }}>
          {helpers =>
            <Form
              autoComplete='off'
              id={formName}
            >
              <DialogTitle id='alert-dialog-title'>
                {`${title} Inventory`}
              </DialogTitle>
              <DialogContent>
                <Grid container spacing={2}>
                  <Grid item xs={12} sm={12}>
                    <Field
                      custom={{ variant: 'outlined', fullWidth: true, }}
                      name='name'
                      label='Name'
                      component={TextField}
                      required
                    />
                  </Grid>
                  <Grid item xs={12} sm={12}>
                    <Field
                      custom={{ variant: 'outlined', fullWidth: true, }}
                      name='productType'
                      label='Product Type'
                      component={TextField}
                      select
                      defaultValue={'DEFAULT'}
                      required
                    >
                      <MenuItem value='DEFAULT' disabled>Choose a product type </MenuItem>
                      {currProducts.map(product =>
                        <MenuItem value={product.name}>{product.name}</MenuItem>
                      )};

                    </Field>

                  </Grid>
                  <Grid item xs={12} sm={12}>
                    <Field
                      custom={{ variant: 'outlined', fullWidth: true, }}
                      name='description'
                      label='Description'
                      component={TextField}
                    />
                  </Grid>
                  <Grid item xs={12} sm={12}>
                    <Field
                      custom={{ variant: 'outlined', fullWidth: true, }}
                      name='averagePrice'
                      label='Average Price'
                      component={TextField}
                      type='number'
                    />
                  </Grid>
                  <Grid item xs={12} sm={12}>
                    <Field
                      custom={{ variant: 'outlined', fullWidth: true, }}
                      name='amount'
                      label='Amount'
                      component={TextField}
                      type='number'
                    />
                  </Grid>
                  <Grid item xs={12} sm={12}>
                    <Field
                      custom={{ variant: 'outlined', fullWidth: true, }}
                      name='unitOfMeasurement'
                      label='Unit Of Measurement'
                      component={TextField}
                      select
                      required
                      defaultValue={'DEFAULT'}
                    >
                      <MenuItem value='DEFAULT' disabled>Choose a unit</MenuItem>
                      {units.map(unit =>
                        <MenuItem value={unit}>{unit}</MenuItem>
                      )};
                    </Field>
                  </Grid>
                  <Grid item xs={12} sm={12}>
                    <Field
                      custom={{ variant: 'outlined', fullWidth: true, }}
                      name='bestBeforeDate'
                      label='Best Before Date'
                      component={TextField}
                      type='date'
                    />
                  </Grid>
                  <Grid item xs={12} sm={12}>
                    <Field
                      custom={{ variant: 'outlined', fullWidth: true, }}
                      name='neverExpires'
                      label='Never Expires'
                      component={FormControlLabel}
                      control= {<Checkbox defaultChecked={initialValues.neverExpires} id='neverExpires'/>}
                    />
                  </Grid>
                </Grid>
              </DialogContent>
              <DialogActions>
                <Button onClick={() => { handleDialog(false) }} color='secondary'>Cancel</Button>
                <Button
                  disableElevation
                  variant='contained'
                  type='submit'
                  form={formName}
                  color='secondary'
                  disabled={!helpers.dirty}
                >
                  Save
                </Button>
              </DialogActions>
            </Form>
          }
        </Formik>
      </Dialog>
    )
  }
}

export default InventoryFormModal
