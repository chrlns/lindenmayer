/*
 *  Lindenmayer
 *  see AUTHORS for a list of contributors.
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package lindenmayer.grammar;

/**
 * This class represents a symbol of an alphabet.
 *
 * @author Christian Lins
 * @author Kai Ritterbusch
 */
public abstract class Symbol {

    public static Symbol create(String text) {
        char c = text.charAt(0);
        if (Character.isUpperCase(c)) {
            return new Variable(text);
        } else {
            return new Constant(text);
        }
    }

    private String text;

    protected Symbol(String text) {
        assert text != null;

        this.text = text.trim();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Symbol) {
            return this.text.equals(((Symbol) obj).text);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return this.text.hashCode();
    }

    @Override
    public String toString() {
        return this.text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
