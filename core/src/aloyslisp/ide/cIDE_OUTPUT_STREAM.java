/**
 * aloysLisp.
 * <p>
 * A LISP interpreter, compiler and library.
 * <p>
 * Copyright (C) 2010 kilroySoft <aloyslisp@kilroysoft.ch>
 * 
 * <p>
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * <p>
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * <p>
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
// --------------------------------------------------------------------------
// history
// --------------------------------------------------------------------------
// IP 16 mars 2011 Creation
// --------------------------------------------------------------------------

package aloyslisp.ide;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import static aloyslisp.core.L.*;
import aloyslisp.core.L;
import aloyslisp.core.tT;
import aloyslisp.core.streams.*;

/**
 * cIDE_OUTPUT_STREAM
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public class cIDE_OUTPUT_STREAM extends cOUTPUT_STREAM
{
	protected JPanel	f_panel			= null;

	protected JTextArea	f_outputArea	= null;

	/**
	 * 
	 */
	public cIDE_OUTPUT_STREAM(int rows, int cols)
	{
		f_panel = new JPanel();
		f_outputArea = new JTextArea(rows, cols);
		f_outputArea.setBackground(Color.white);
		f_outputArea.setFont(new Font("Courier", Font.PLAIN, 12));
		f_outputArea.setEditable(false);
		f_outputArea.setLineWrap(true);
		f_outputArea.setVisible(true);

		JScrollPane scroller = new JScrollPane(f_outputArea,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		f_panel.add(scroller);
		L.standardOutput.SET_SYMBOL_VALUE(this);
		L.errorOutput.SET_SYMBOL_VALUE(this);
	}

	/**
	 * @return
	 */
	public JPanel getPanel()
	{
		return f_panel;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.streams.tOUTPUT_STREAM#WRITE_CHAR(java.lang.Character)
	 */
	@Override
	public Character WRITE_CHAR(Character character)
	{
		f_outputArea.append("" + character);
		return character;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.streams.tOUTPUT_STREAM#WRITE_BYTE(java.lang.Integer)
	 */
	@Override
	public Integer WRITE_BYTE(Integer val)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.streams.tOUTPUT_STREAM#FINISH_OUTPUT()
	 */
	@Override
	public tT FINISH_OUTPUT()
	{
		return T;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.streams.tOUTPUT_STREAM#FORCE_OUTPUT()
	 */
	@Override
	public tT FORCE_OUTPUT()
	{
		return T;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.streams.tOUTPUT_STREAM#CLEAR_OUTPUT()
	 */
	@Override
	public tT CLEAR_OUTPUT()
	{
		f_outputArea.setText("");
		return T;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.streams.tSTREAM#OPEN_STREAM_P()
	 */
	@Override
	public Boolean OPEN_STREAM_P()
	{
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.streams.tSTREAM#CLOSE()
	 */
	@Override
	public Boolean CLOSE()
	{
		return false;
	}

}
