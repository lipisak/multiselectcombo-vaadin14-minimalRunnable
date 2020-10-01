/*
 * Copyright (C) 2018 MEDIA SOLUTIONS
 * All Rights Reserved.  No use, copying or distribution of this
 * work may be made except in accordance with a valid license
 * agreement from MEDIA SOLUTIONS. This notice must be
 * included on all copies, modifications and derivatives of this
 * work.
 *
 * MEDIA SOLUTIONS MAKES NO REPRESENTATIONS OR WARRANTIES
 * ABOUT THE SUITABILITY OF THE SOFTWARE, EITHER EXPRESSED OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE, OR NON-INFRINGEMENT.
 * MEDIA SOLUTIONS SHALL NOT BE LIABLE FOR ANY DAMAGES
 * SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING
 * THIS SOFTWARE OR ITS DERIVATIVES.
 *
 */
package org.vaadin.example;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ItemLabelGenerator;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import org.apache.commons.lang3.StringUtils;
import org.vaadin.gatanaso.MultiselectComboBox;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;

/**
 * @author Jiri Slivarich, jiri.slivarich@media-sol.com, MEDIA SOLUTIONS
 */
public class MultiComboBoxField<T> extends CustomField<List<T>> {

	private static final long serialVersionUID = -2324173743284396918L;

	private final MultiselectComboBox<T> comboBox = new MultiselectComboBox<>();
	private List<T> value = getEmptyValue();
	private boolean ignoreValueChange;

	public MultiComboBoxField() {
		initComboBox();
		add(initContent());
	}
	
	private void initComboBox() {
		comboBox.addValueChangeListener(e -> {
			if (ignoreValueChange) return;
            
			List<T> old = getValue();
            List<T> value = new ArrayList<>(e.getValue());

			setValue(value);
			fireEvent(new ComponentValueChangeEvent<>(MultiComboBoxField.this, MultiComboBoxField.this, old, true));
		});
		comboBox.setItemLabelGenerator(item -> Optional.ofNullable(item).map(Object::toString).orElse(StringUtils.EMPTY));
		comboBox.setWidthFull();
	}
	
	@Override
	public List<T> getValue() {
		return value;
	}

	private Component initContent() {
		HorizontalLayout layout = new HorizontalLayout();
		layout.setWidthFull();
		layout.add(comboBox);

		return layout;
	}

	@Override
	protected void setPresentationValue(List<T> newPresentationValue) {
		ignoreValueChange = true;
		if (newPresentationValue == null) {
			this.value = getEmptyValue();
			comboBox.clear();
		} else {
			this.value = new ArrayList<>(newPresentationValue);
			comboBox.setValue(new LinkedHashSet<>(newPresentationValue));
		}
		ignoreValueChange = false;
	}

	@Override
	public List<T> getEmptyValue() {
		return new ArrayList<>();
	}
	
	public MultiComboBoxField<T> withItems(List<T> items) {
		comboBox.setItems(items);
		return this;
	}
	
	public MultiComboBoxField<T> withOrdered(boolean ordered) {
		comboBox.setOrdered(ordered);
		return this;
	}

	@Override
	public void setInvalid(boolean invalid) {
		super.setInvalid(invalid);
		comboBox.setInvalid(invalid);
	}

	@Override
	public void setReadOnly(boolean readOnly) {
		super.setReadOnly(readOnly);
		comboBox.setReadOnly(readOnly);
	}

	public MultiComboBoxField<T> withItemCaptionGenerator(ItemLabelGenerator<T> itemCaptionGenerator) {
		comboBox.setItemLabelGenerator(itemCaptionGenerator);
		return this;
	}
	
	@Override
	protected List<T> generateModelValue() {
		return new ArrayList<>(comboBox.getSelectedItems());
	}
}
